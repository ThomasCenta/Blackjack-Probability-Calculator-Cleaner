package DealerProbabilityCalculatorTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import DealerCalculator.DealerDeck;
import DealerCalculator.DealerProbabilityCalculator;
import DealerCalculator.DealerHand;
import General.Rules;

public class DealerProbabilityCalculatorBruteCheckerUnitTests {


	private static void printArr(double[] arr) {
		System.out.print(arr[0]);
		for(int i = 1; i < arr.length; i += 1) {
			System.out.print(" "+arr[i]);
		}
		System.out.println("");
	}

	@Test
	public void emptyHandOneDeckTest() {
		boolean hitOnSoft17 = true;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		//reminder: {nothing,     Ace,2,3,4,5,6,7,8,9,10}
		int[] numDealerCards =   {0,0,0,0,0,0,0,0,0,0,0};
		int[] numInitCalcCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numDeckCards =     {0,4,4,4,4,4,4,4,4,4,16};
		//note numDeckCards should have dealer cards already taken out

		//start the test
		DealerHand dealerHand = new DealerHand();
		DealerHand initialHand = new DealerHand();
		DealerDeck dealerDeck = new DealerDeck();
		for(int i = 1; i <= 10; i += 1) {
			for(int j = 0; j < numDealerCards[i]; j += 1) {dealerHand.addCard(i);}
			for(int j = 0; j < numDeckCards[i]; j += 1) {dealerDeck.addCard(i);}
			for(int j = 0; j < numInitCalcCards[i]; j += 1) {initialHand.addCard(i);}
		}

		Rules rules = new Rules(hitOnSoft17, numTimesAllowedSplitting, numTimesAllowedSplittingAces,
				blackjackAfterSplittingAces, blackjackPayout, noHitSplitAces, doubleHardValuesAllowed);
		DealerProbabilityCalculator calc = new DealerProbabilityCalculator(rules, initialHand);


		double[] expectedResult = BruteForceDealerProbabilityCalculator.dealerResult(rules, dealerDeck, dealerHand, 1.0);
		double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
		for(int i = 0 ; i < expectedResult.length; i += 1) {
			assertEquals(expectedResult[i], result[i], 0.000001);
		}
	}

	@Test
	public void emptyHandOneDeckTestNoHitSoft17() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		//reminder: {nothing,     Ace,2,3,4,5,6,7,8,9,10}
		int[] numDealerCards =   {0,0,0,0,0,0,0,0,0,0,0};
		int[] numInitCalcCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numDeckCards =     {0,4,4,4,4,4,4,4,4,4,16};
		//note numDeckCards should have dealer cards already taken out

		//start the test
		DealerHand dealerHand = new DealerHand();
		DealerHand initialHand = new DealerHand();
		DealerDeck dealerDeck = new DealerDeck();
		for(int i = 1; i <= 10; i += 1) {
			for(int j = 0; j < numDealerCards[i]; j += 1) {dealerHand.addCard(i);}
			for(int j = 0; j < numDeckCards[i]; j += 1) {dealerDeck.addCard(i);}
			for(int j = 0; j < numInitCalcCards[i]; j += 1) {initialHand.addCard(i);}
		}

		Rules rules = new Rules(hitOnSoft17, numTimesAllowedSplitting, numTimesAllowedSplittingAces,
				blackjackAfterSplittingAces, blackjackPayout, noHitSplitAces, doubleHardValuesAllowed);
		DealerProbabilityCalculator calc = new DealerProbabilityCalculator(rules, initialHand);

		double[] expectedResult = BruteForceDealerProbabilityCalculator.dealerResult(rules, dealerDeck, dealerHand, 1.0);
		double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
		for(int i = 0 ; i < expectedResult.length; i += 1) {
			assertEquals(expectedResult[i], result[i], 0.000001);
		}
	}

	@Test
	public void allOneCardHandsOneDeck() {
		boolean hitOnSoft17 = true;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		Rules rules = new Rules(hitOnSoft17, numTimesAllowedSplitting, numTimesAllowedSplittingAces,
				blackjackAfterSplittingAces, blackjackPayout, noHitSplitAces, doubleHardValuesAllowed);

		for(int rank = 1; rank <= 10; rank += 1) {
			DealerHand dealerHand = new DealerHand();
			dealerHand.addCard(rank);
			DealerHand initialHand = new DealerHand(dealerHand);
			DealerDeck dealerDeck = new DealerDeck(1);
			dealerDeck.removeCard(rank);
			DealerProbabilityCalculator calc = new DealerProbabilityCalculator(rules, initialHand);

			double[] expectedResult = BruteForceDealerProbabilityCalculator.dealerResult(rules, dealerDeck, dealerHand, 1.0);
			double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
			for(int i = 0 ; i < expectedResult.length; i += 1) {
				assertEquals(expectedResult[i], result[i], 0.000001);
			}
		}
	}
	
	@Test
	public void allOneCardHandsOneDeckNoHitOnSoft17() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		Rules rules = new Rules(hitOnSoft17, numTimesAllowedSplitting, numTimesAllowedSplittingAces,
				blackjackAfterSplittingAces, blackjackPayout, noHitSplitAces, doubleHardValuesAllowed);

		for(int rank = 1; rank <= 10; rank += 1) {
			DealerHand dealerHand = new DealerHand();
			dealerHand.addCard(rank);
			DealerHand initialHand = new DealerHand(dealerHand);
			DealerDeck dealerDeck = new DealerDeck(1);
			dealerDeck.removeCard(rank);
			DealerProbabilityCalculator calc = new DealerProbabilityCalculator(rules, initialHand);

			double[] expectedResult = BruteForceDealerProbabilityCalculator.dealerResult(rules, dealerDeck, dealerHand, 1.0);
			double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
			for(int i = 0 ; i < expectedResult.length; i += 1) {
				assertEquals(expectedResult[i], result[i], 0.000001);
			}
		}
	}

	@Test
	public void allTwoCardHandsOneDeck() {
		boolean hitOnSoft17 = true;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		Rules rules = new Rules(hitOnSoft17, numTimesAllowedSplitting, numTimesAllowedSplittingAces,
				blackjackAfterSplittingAces, blackjackPayout, noHitSplitAces, doubleHardValuesAllowed);

		for(int rankOne = 1; rankOne <= 10; rankOne += 1) {
			for(int rankTwo = rankOne; rankTwo <= 10; rankTwo += 1) {
				DealerHand dealerHand = new DealerHand();
				dealerHand.addCard(rankOne);
				dealerHand.addCard(rankTwo);
				DealerHand initialHand = new DealerHand(dealerHand);
				DealerDeck dealerDeck = new DealerDeck(1);
				dealerDeck.removeCard(rankOne);
				dealerDeck.removeCard(rankTwo);
				DealerProbabilityCalculator calc = new DealerProbabilityCalculator(rules, initialHand);

				double[] expectedResult = BruteForceDealerProbabilityCalculator.dealerResult(rules, dealerDeck, dealerHand, 1.0);
				double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
				for(int i = 0 ; i < expectedResult.length; i += 1) {
					assertEquals(expectedResult[i], result[i], 0.000001);
				}
			}
		}
	}
	
	@Test
	public void allTwoCardHandsOneDeckNoHitOnSoft17() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		Rules rules = new Rules(hitOnSoft17, numTimesAllowedSplitting, numTimesAllowedSplittingAces,
				blackjackAfterSplittingAces, blackjackPayout, noHitSplitAces, doubleHardValuesAllowed);

		for(int rankOne = 1; rankOne <= 10; rankOne += 1) {
			for(int rankTwo = rankOne; rankTwo <= 10; rankTwo += 1) {
				DealerHand dealerHand = new DealerHand();
				dealerHand.addCard(rankOne);
				dealerHand.addCard(rankTwo);
				DealerHand initialHand = new DealerHand(dealerHand);
				DealerDeck dealerDeck = new DealerDeck(1);
				dealerDeck.removeCard(rankOne);
				dealerDeck.removeCard(rankTwo);
				DealerProbabilityCalculator calc = new DealerProbabilityCalculator(rules, initialHand);

				double[] expectedResult = BruteForceDealerProbabilityCalculator.dealerResult(rules, dealerDeck, dealerHand, 1.0);
				double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
				for(int i = 0 ; i < expectedResult.length; i += 1) {
					assertEquals(expectedResult[i], result[i], 0.000001);
				}
			}
		}
	}
	
	@Test
	public void allTwoCardHandsOneDeckNoHitOnSoft17OneCalculator() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		Rules rules = new Rules(hitOnSoft17, numTimesAllowedSplitting, numTimesAllowedSplittingAces,
				blackjackAfterSplittingAces, blackjackPayout, noHitSplitAces, doubleHardValuesAllowed);

		DealerProbabilityCalculator calc = new DealerProbabilityCalculator(rules, new DealerHand());
		for(int rankOne = 1; rankOne <= 10; rankOne += 1) {
			for(int rankTwo = rankOne; rankTwo <= 10; rankTwo += 1) {
				DealerHand dealerHand = new DealerHand();
				dealerHand.addCard(rankOne);
				dealerHand.addCard(rankTwo);
				DealerDeck dealerDeck = new DealerDeck(1);
				dealerDeck.removeCard(rankOne);
				dealerDeck.removeCard(rankTwo);
				

				double[] expectedResult = BruteForceDealerProbabilityCalculator.dealerResult(rules, dealerDeck, dealerHand, 1.0);
				double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
				for(int i = 0 ; i < expectedResult.length; i += 1) {
					assertEquals(expectedResult[i], result[i], 0.000001);
				}
			}
		}
	}
}
