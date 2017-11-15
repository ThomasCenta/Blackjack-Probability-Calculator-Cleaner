package PlayerCalculatorTests;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import PlayerCalculator.DealerHand;
import PlayerCalculator.DealerProbabilityCalculator;
import PlayerCalculator.Rules;
import PlayerCalculator.VariableRankDeck;
import PlayerCalculator.VariableRankHand;

/*
 * This will not be pure brute force because that would take so long to calculate.
 * It will not be as smart as the main calculator though, it will just store created hands.
 */
public class AlternatePlayerCalculator {

	public static final double IMPOSSIBLE_CODE = -100000;
	public static final int STAYING_INDEX = 0;
	public static final int DOUBLING_INDEX = 1;
	public static final int HITTING_INDEX = 2;
	

	private class MoneyInfo{
		public final double moneyMadeIfStaying;
		public final double moneyMadeIfDoubling;
		public final double moneyMadeIfHitting;
		public final double mostMoney;


		public MoneyInfo(double moneyMadeIfStaying, double moneyMadeIfDoubling, double moneyMadeIfHitting) {
			this.moneyMadeIfStaying = moneyMadeIfStaying;
			this.moneyMadeIfDoubling = moneyMadeIfDoubling;
			this.moneyMadeIfHitting = moneyMadeIfHitting;
			this.mostMoney = Math.max(moneyMadeIfDoubling, Math.max(moneyMadeIfHitting, moneyMadeIfStaying));
		}

		public double[] createResults() {
			double[] result = new double[3];
			result[STAYING_INDEX] = this.moneyMadeIfStaying;
			result[DOUBLING_INDEX] = this.moneyMadeIfDoubling;
			result[HITTING_INDEX] = this.moneyMadeIfHitting;
			return result;
		}
	}

	private Map<VariableRankHand, MoneyInfo> allFullyCalculatedHands;
	private VariableRankDeck baseDeck;
	private VariableRankDeck deckWithNoDealer;
	private Rules rules;
	private VariableRankHand dealerHand;
	private double[] dealerResult;
	private DealerProbabilityCalculator dealerCalculator;

	/*
	 * deck should have dealerHand in it
	 */
	public AlternatePlayerCalculator(VariableRankHand dealerHand, VariableRankDeck deck, Rules rules) {
		this.allFullyCalculatedHands = new HashMap<VariableRankHand, MoneyInfo>();
		this.baseDeck = new VariableRankDeck(deck);
		this.deckWithNoDealer = new VariableRankDeck(deck);
		this.deckWithNoDealer.takeOutHand(dealerHand);
		this.rules = rules;
		this.dealerHand = new VariableRankHand(dealerHand);
		this.dealerResult = rules.dealerResult(new DealerHand(dealerHand));
		this.dealerCalculator = new DealerProbabilityCalculator(rules, new DealerHand(dealerHand));
	}

	/*
	 * Leaf => player cannot double or hit
	 * makes a copy of playerHand
	 */
	private MoneyInfo createAddAndReturnLeafMoneyInfo(VariableRankHand playerHand) {
		MoneyInfo info = new MoneyInfo(rules.moneyMadeOnStaying(playerHand,this.dealerResult), IMPOSSIBLE_CODE, IMPOSSIBLE_CODE);
		this.allFullyCalculatedHands.put(new VariableRankHand(playerHand), info);
		return info;
	}

	private MoneyInfo createAddAndReturnMiddleMoneyInfo(VariableRankHand playerHand) {
		//this is horribly written. Try to fix it later
		double stayingMoney = rules.moneyMadeOnStaying(playerHand,this.dealerResult);
		double doublingMoney = 0;
		double hittingMoney = 0;
		boolean allowedToDouble = rules.allowedToDouble(playerHand);
		boolean allowedToHit = rules.playerAllowedToHit(playerHand);
		for(int i = 1; i <= 13; i += 1) {
			double probability = this.deckWithNoDealer.drawProbability13(i, playerHand);
			if(probability > 0) {
				playerHand.addCard(i);
				MoneyInfo info = getOrCalculateMoneyInfo(playerHand);
				if(allowedToDouble) {doublingMoney += probability*info.moneyMadeIfStaying;}
				if(allowedToHit) {hittingMoney += probability*info.mostMoney;}
				playerHand.removeCard(i);
			}
		}
		if(!allowedToDouble) {doublingMoney = IMPOSSIBLE_CODE;}
		if(!allowedToHit) {hittingMoney = IMPOSSIBLE_CODE;}
		MoneyInfo info = new MoneyInfo(stayingMoney, doublingMoney, hittingMoney);
		this.allFullyCalculatedHands.put(new VariableRankHand(playerHand), info);
		return info;
	}

	/*
	 * adds the moneyInfo to this if not calculated already
	 */
	private MoneyInfo getOrCalculateMoneyInfo(VariableRankHand playerHand) {
		if(this.allFullyCalculatedHands.containsKey(playerHand)) {
			return this.allFullyCalculatedHands.get(playerHand);
		}
		if(!rules.playerAllowedToContinue(playerHand)) {
			return createAddAndReturnLeafMoneyInfo(playerHand);
		}
		return createAddAndReturnMiddleMoneyInfo(playerHand);
	}


	/*
	 * deck should already have both hands taken out
	 */
	public double[] results(VariableRankHand playerHand) {
		this.allFullyCalculatedHands = new HashMap<VariableRankHand, MoneyInfo>();
		return getOrCalculateMoneyInfo(playerHand).createResults();
	}
}
