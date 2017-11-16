package PlayerCalculatorTests;

import PlayerCalculator.PlayerCalculator;
import PlayerCalculator.Rules;
import PlayerCalculator.VariableRankDeck;
import PlayerCalculator.VariableRankHand;

public class PlayerCalculatorSimulationTest {

	private static VariableRankHand dealerHand;
	private static VariableRankHand playerHand;
	private static VariableRankDeck baseDeck;
	private static PlayerCalculator calc;
	private static Rules rules;
	private static final int NUM_TESTS = 1000000;
	
	private static void setup() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.2;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;
		
		rules = new Rules(hitOnSoft17, numTimesAllowedSplitting, numTimesAllowedSplittingAces,
				blackjackAfterSplittingAces, blackjackPayout, noHitSplitAces, doubleHardValuesAllowed);
		
		//					nothing, ace,2,3,4,5,6,7,8,9,10,jack,queen,king
		int[] playerCards = 		{0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int[] dealerHandCards = 	{0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int[] deckCards = 			{0,4,4,4,4,4,4,4,4,4,4,4,4,4};
		
		playerHand = new VariableRankHand();
		for(int i = 1; i <= 13; i += 1) {
			for(int j = 0; j < playerCards[i]; j += 1) {
				playerHand.addCard(i);
			}
		}

		dealerHand = new VariableRankHand();
		for(int i = 1; i <= 13; i += 1) {
			for(int j = 0; j < dealerHandCards[i]; j += 1) {
				dealerHand.addCard(i);
			}
		}

		baseDeck = new VariableRankDeck();
		for(int i = 1; i <= 13; i += 1) {
			for(int j = 0; j < deckCards[i]; j += 1) {
				baseDeck.addCard(i);
			}
		}
	}

	
	
	private static int largestIndex(double[] arr) {
		int largestIndex = 0;
		double largestValue = arr[0];
		for(int i = 1; i < arr.length; i += 1) {
			if(arr[i] > largestValue) {
				largestValue = arr[i];
				largestIndex = i;
			}
		}
		return largestIndex;
	}
	
	/*
	 * simulates the player, but not the dealer.
	 * alters hands and deck
	 */
	public static double runSimulationCalculateDealer(VariableRankHand playerCurrentHand, VariableRankDeck deck) {
		double[] results = calc.results(playerCurrentHand);
		int bestMove = largestIndex(results);
		if(bestMove == PlayerCalculator.STAYING_INDEX) {
			return results[0];
		}else if(bestMove == PlayerCalculator.DOUBLING_INDEX) {
			playerCurrentHand.addCard(deck.removeRandomCard());
			return 2*calc.results(playerCurrentHand)[0];
		}else if(bestMove == PlayerCalculator.HITTING_INDEX) {
			playerCurrentHand.addCard(deck.removeRandomCard());
			return runSimulationCalculateDealer(playerCurrentHand, deck);
		}else {
			assert false;
		}
		return 0;
	}
	
	
	public static void main(String[] args) {
		setup();
		calc = new PlayerCalculator(dealerHand, baseDeck, rules);
		double[] predictedResults = calc.results(playerHand);
		double predictedMoneyMade = predictedResults[largestIndex(predictedResults)];
		
		VariableRankDeck noHandDeck = new VariableRankDeck(baseDeck);
		noHandDeck.takeOutHand(dealerHand);
		noHandDeck.takeOutHand(playerHand);
		
		double totalMoney = 0;
		for(int i = 0; i < NUM_TESTS; i += 1) {
			totalMoney += runSimulationCalculateDealer(new VariableRankHand(playerHand), new VariableRankDeck(noHandDeck));
		}
		System.out.println("Average simulation money = "+totalMoney/NUM_TESTS);
		System.out.println("predicted money = "+predictedMoneyMade);
		System.out.println("difference = "+(totalMoney/NUM_TESTS-predictedMoneyMade));
	}
}
