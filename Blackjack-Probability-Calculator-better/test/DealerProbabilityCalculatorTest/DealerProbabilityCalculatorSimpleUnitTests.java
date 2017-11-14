package DealerProbabilityCalculatorTest;

import static org.junit.Assert.*;

import org.junit.Test;

import DealerCalculator.DealerDeck;
import DealerCalculator.DealerProbabilityCalculator;
import DealerCalculator.DealerHand;
import General.Rules;

public class DealerProbabilityCalculatorSimpleUnitTests {

	private double sum(double[] arr) {
		double sum = 0.0;
		for(int i = 0; i < arr.length; i += 1) {
			sum += arr[i];
		}
		return sum;
	}

	@Test
	public void testDealerProbabilitiesSumToOneWithTwoCards() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		int[] numDealerCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numInitCalcCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numDeckCards = {0,1,0,0,0,0,0,0,0,0,1};
		
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
		assertEquals(1.0, sum(calc.dealerProbabilities(dealerDeck, dealerHand)), 0.00001);
	}
	
	@Test
	public void testDealerProbabilitiesSumToOneWithFullDeck() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		int[] numDealerCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numInitCalcCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numDeckCards = {4,4,4,4,4,4,4,4,4,4,4};
		
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
		assertEquals(1.0, sum(calc.dealerProbabilities(dealerDeck, dealerHand)), 0.00001);
	}
	
	@Test
	public void testDealerProbabilitiesBlackjackOnly() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		int[] numDealerCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numInitCalcCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numDeckCards = {0,1,0,0,0,0,0,0,0,0,1};
		
		double[] expectedResult = {0,0,0,0,0,1.0,0};
		
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
		
		double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
		for(int i = 0 ; i < expectedResult.length; i += 1) {
			assertEquals(expectedResult[i], result[i], 0.000001);
		}
	}
	
	@Test
	public void testDealerProbabilitiesTwoSameCards() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		int[] numDealerCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numInitCalcCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numDeckCards = {0,0,0,0,0,0,0,0,0,0,2};
		
		double[] expectedResult = {0,0,0,1,0,0,0};
		
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
		
		double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
		for(int i = 0 ; i < expectedResult.length; i += 1) {
			assertEquals(expectedResult[i], result[i], 0.000001);
		}
	}
	
	
	@Test
	public void testDealerProbabilitiesNoPossibleOutcome() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		//reminder: {nothing, Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10}
		int[] numDealerCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numInitCalcCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numDeckCards = {0,0,1,0,0,0,0,0,0,1,0};
		
		//reminder: {17, 18, 19, 20, non-blackjack 21, blackjack, bust}
		double[] expectedResult = {0,0,0,0,0,0,0};
		
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
		
		double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
		for(int i = 0 ; i < expectedResult.length; i += 1) {
			assertEquals(expectedResult[i], result[i], 0.000001);
		}
	}
	
	
	@Test
	public void testDealerProbabilitiesNonBlackjack21() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		//reminder: {nothing, Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10}
		int[] numDealerCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numInitCalcCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numDeckCards = {0,0,0,0,0,0,1,1,1,0,0};
		
		//reminder: {17, 18, 19, 20, non-blackjack 21, blackjack, bust}
		double[] expectedResult = {0,0,0,0,1,0,0};
		
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
		
		double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
		for(int i = 0 ; i < expectedResult.length; i += 1) {
			assertEquals(expectedResult[i], result[i], 0.000001);
		}
	}
	
	@Test
	public void testDealerProbabilitiesEmptyDeckWithBlackjack() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		//reminder: {nothing, Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10}
		int[] numDealerCards = {0,1,0,0,0,0,0,0,0,0,1};
		int[] numInitCalcCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numDeckCards = {0,0,0,0,0,0,0,0,0,0,0};
		
		//reminder: {17, 18, 19, 20, non-blackjack 21, blackjack, bust}
		double[] expectedResult = {0,0,0,0,0,1,0};
		
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
		
		double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
		for(int i = 0 ; i < expectedResult.length; i += 1) {
			assertEquals(expectedResult[i], result[i], 0.000001);
		}
	}
	
	@Test
	public void testDealerProbabilitiesTwoEquallyLikelyOutcomes() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		//reminder: {nothing, Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10}
		int[] numDealerCards = {0,0,0,0,0,0,0,0,0,0,1};
		int[] numInitCalcCards = {0,0,0,0,0,0,0,0,0,0,1};
		int[] numDeckCards = {0,0,0,0,0,0,0,0,0,1,1};
		
		//reminder: {17, 18, 19, 20, non-blackjack 21, blackjack, bust}
		double[] expectedResult = {0,0,0.5,0.5,0,0,0};
		
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
		
		double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
		for(int i = 0 ; i < expectedResult.length; i += 1) {
			assertEquals(expectedResult[i], result[i], 0.000001);
		}
	}
	
	@Test
	public void testDealerProbabilitiesWithTwoUnequallyLikelyOutcomes() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		//reminder: {nothing, Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10}
		int[] numDealerCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numInitCalcCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numDeckCards = {0,1,0,0,0,0,1,0,0,0,1};
		
		//reminder: {17, 18, 19, 20, non-blackjack 21, blackjack, bust}
		double[] expectedResult = {0.666666666,0,0,0,0,0.333333333,0};
		
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
		
		double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
		for(int i = 0 ; i < expectedResult.length; i += 1) {
			assertEquals(expectedResult[i], result[i], 0.000001);
		}
	}
	
	@Test
	public void testDealerProbabilitiesHitOnSoft17RuleActive() {
		boolean hitOnSoft17 = true;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		//reminder: {nothing, Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10}
		int[] numDealerCards = {0,1,0,0,0,0,1,0,0,0,0};
		int[] numInitCalcCards = {0,1,0,0,0,0,1,0,0,0,0};
		int[] numDeckCards = {0,1,1,1,1,0,0,0,0,0,1};
		
		//reminder: {17, 18, 19, 20, non-blackjack 21, blackjack, bust}
		double[] expectedResult = {1.0/5, 1.0/5, 1.0/5, 1.0/5, 1.0/5, 0, 0};
		
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
		
		double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
		for(int i = 0 ; i < expectedResult.length; i += 1) {
			assertEquals(expectedResult[i], result[i], 0.000001);
		}
	}
	
	@Test
	public void testDealerProbabilitiesHitOnSoft17RuleInActive() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		//reminder: {nothing, Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10}
		int[] numDealerCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numInitCalcCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numDeckCards = {0,1,0,0,0,0,1,0,0,0,0};
		
		//reminder: {17, 18, 19, 20, non-blackjack 21, blackjack, bust}
		double[] expectedResult = {1.0, 0, 0, 0, 0, 0, 0};
		
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
		
		double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
		for(int i = 0 ; i < expectedResult.length; i += 1) {
			assertEquals(expectedResult[i], result[i], 0.000001);
		}
	}
	
	@Test
	public void testDealerProbabilitiesDifferentInitHand() {
		boolean hitOnSoft17 = true;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		//reminder: {nothing, Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10}
		int[] numDealerCards = {0,1,0,0,0,0,1,0,0,0,0};
		int[] numInitCalcCards = {0,0,0,0,0,0,1,0,0,0,0};
		int[] numDeckCards = {0,1,1,1,1,0,0,0,0,0,1};
		
		//reminder: {17, 18, 19, 20, non-blackjack 21, blackjack, bust}
		double[] expectedResult = {1.0/5, 1.0/5, 1.0/5, 1.0/5, 1.0/5, 0, 0};
		
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
		
		double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
		for(int i = 0 ; i < expectedResult.length; i += 1) {
			assertEquals(expectedResult[i], result[i], 0.000001);
		}
	}
	
	
	@Test
	public void testDealerProbabilitiesLargeEvenDeck() {
		boolean hitOnSoft17 = true;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		//reminder: {nothing,     Ace,2,3,4,5,6,7,8,9,10}
		int[] numDealerCards =   {0,0,0,0,0,0,1,0,0,0,1};
		int[] numInitCalcCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numDeckCards =     {0,4,4,4,4,4,4,4,4,4,4};
		
		//reminder: {17, 18, 19, 20, non-blackjack 21, blackjack, bust}
		double[] expectedResult = {4.0/40, 4.0/40, 4.0/40, 4.0/40, 4.0/40, 0, 20.0/40};
		
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
		
		double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
		for(int i = 0 ; i < expectedResult.length; i += 1) {
			assertEquals(expectedResult[i], result[i], 0.000001);
		}
	}
	
	@Test
	public void testDealerProbabilitiesRegularDeckOneCardDraw() {
		boolean hitOnSoft17 = true;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		//reminder: {nothing,     Ace,2,3,4,5,6,7,8,9,10}
		int[] numDealerCards =   {0,0,0,0,0,0,1,0,0,0,1};
		int[] numInitCalcCards = {0,0,0,0,0,0,0,0,0,0,0};
		int[] numDeckCards =     {0,4,4,4,4,4,3,4,4,4,15};
		//note numDeckCards should have dealer cards already taken out
		
		//reminder: {17, 18, 19, 20, non-blackjack 21, blackjack, bust}
		double[] expectedResult = {4.0/50, 4.0/50, 4.0/50, 4.0/50, 4.0/50, 0, 30.0/50};
		
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
		
		double[] result = calc.dealerProbabilities(dealerDeck, dealerHand);
		for(int i = 0 ; i < expectedResult.length; i += 1) {
			assertEquals(expectedResult[i], result[i], 0.000001);
		}
	}
	
}
