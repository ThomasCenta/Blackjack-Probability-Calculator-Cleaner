package DealerProbabilityCalculatorTest;

import PlayerCalculator.DealerDeck;
import PlayerCalculator.DealerHand;
import PlayerCalculator.Rules;

public class BruteForceDealerProbabilityCalculator {

	private static void printArr(double[] arr) {
		System.out.print(arr[0]);
		for(int i = 1; i < arr.length; i += 1) {
			System.out.print(" "+arr[i]);
		}
		System.out.println("");
	}
	
	private static void multiplyAll(double[] multiplyThis, double multiplyBy) {
		for(int i = 0; i < multiplyThis.length; i += 1) {
			multiplyThis[i] *= multiplyBy;
		}
	}
	
	private static void vectorAdd(double[] addTo, double[] addFrom) {
		for(int i = 0; i < addTo.length; i += 1) {
			addTo[i] += addFrom[i];
		}
	}
	
	/*
	 * make sure hand is already taken out of deck
	 */
	public static double[] dealerResult(Rules rules, DealerDeck deck, DealerHand hand, double probability) {
		if(rules.dealerStays(hand)) {
			double[] toReturn = rules.dealerResult(hand);
			multiplyAll(toReturn, probability);
			return toReturn;
		}
		
		double[] dealerResult = new double[7];
		for(int rank = 1; rank <= 10; rank += 1) {
			if(deck.numCard(rank) > 0) {
				double handProbability = probability * deck.drawProbability(rank);
				deck.removeCard(rank);
				hand.addCard(rank);
				
				double[] result = dealerResult(rules, deck, hand, handProbability);
				vectorAdd(dealerResult, result);
				
				hand.removeCard(rank);
				deck.addCard(rank);
			}
		}
		return dealerResult;
	}
	
	public static void main(String[] args) {
		boolean hitOnSoft17 = true;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		//reminder: {nothing,     Ace,2,3,4,5,6,7,8,9,10}
		int[] numDealerCards =   {0,0,0,0,0,0,0,0,0,0,0};
		int[] numDeckCards =     {0,4,4,4,4,4,4,4,4,4,15};
		
		DealerHand dealerHand = new DealerHand();
		DealerDeck dealerDeck = new DealerDeck();
		for(int i = 1; i <= 10; i += 1) {
			for(int j = 0; j < numDealerCards[i]; j += 1) {dealerHand.addCard(i);}
			for(int j = 0; j < numDeckCards[i]; j += 1) {dealerDeck.addCard(i);}
		}
		
		Rules rules = new Rules(hitOnSoft17, numTimesAllowedSplitting, numTimesAllowedSplittingAces,
				blackjackAfterSplittingAces, blackjackPayout, noHitSplitAces, doubleHardValuesAllowed);
		
		double[] result = dealerResult(rules, dealerDeck, dealerHand, 1.0);
		printArr(result);
		
	}
}
