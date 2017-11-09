package DealerProbabilityCalculatorTest;

import static org.junit.Assert.*;

import org.junit.Test;

import DealerCalculator.DealerDeck;
import DealerCalculator.DealerHand;
import DealerCalculator.DealerProbabilityCalculator;
import General.Rules;

public class DealerProbabilityCalculatorTest {

	private double sum(double[] arr) {
		double sum = 0.0;
		for(int i = 0; i < arr.length; i += 1) {
			sum += arr[i];
		}
		return sum;
	}

	@Test
	public void testDealerProbabilitiesSum() {
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
}
