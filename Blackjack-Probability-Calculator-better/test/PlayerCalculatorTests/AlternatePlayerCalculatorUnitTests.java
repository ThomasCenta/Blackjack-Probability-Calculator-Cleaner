package PlayerCalculatorTests;

import static org.junit.Assert.*;

import org.junit.Test;

import PlayerCalculator.Rules;
import PlayerCalculator.VariableRankDeck;
import PlayerCalculator.VariableRankHand;

public class AlternatePlayerCalculatorUnitTests {

	@Test
	public void checkConstructorHasNoErrorsDealerBlackjack() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		//	    nothing, ace,2,3,4,5,6,7,8,9,10,jack,queen,king
		int[] playerStartingPoint = {0,1,0,0,0,0,0,0,0,0,1,0,0,0};
		int[] dealerHandCards = 	{0,1,0,0,0,0,0,0,0,0,1,0,0,0};
		int[] deckCards = 			{0,40,40,40,40,40,40,40,40,40,40,40,40,40};

		//end variable choices
		VariableRankHand playerStartingHand = new VariableRankHand();
		for(int i = 1; i <= 13; i += 1) {
			for(int j = 0; j < playerStartingPoint[i]; j += 1) {
				playerStartingHand.addCard(i);
			}
		}

		VariableRankHand dealerHand = new VariableRankHand();
		for(int i = 1; i <= 13; i += 1) {
			for(int j = 0; j < dealerHandCards[i]; j += 1) {
				dealerHand.addCard(i);
			}
		}

		VariableRankDeck deck = new VariableRankDeck();
		for(int i = 1; i <= 13; i += 1) {
			for(int j = 0; j < deckCards[i]; j += 1) {
				deck.addCard(i);
			}
		}

		Rules rules = new Rules(hitOnSoft17, numTimesAllowedSplitting, numTimesAllowedSplittingAces,
				blackjackAfterSplittingAces, blackjackPayout, noHitSplitAces, doubleHardValuesAllowed);

		@SuppressWarnings("unused")
		AlternatePlayerCalculator calc = new AlternatePlayerCalculator(dealerHand, deck, rules);
	}
	
	@Test
	public void checkPlayerBlackjackBeatsDealerNonblackjack() {
		boolean hitOnSoft17 = false;
		int numTimesAllowedSplitting = 0;
		int numTimesAllowedSplittingAces = 0;
		boolean blackjackAfterSplittingAces = false;
		double blackjackPayout = 1.5;
		boolean noHitSplitAces = false;
		int[] doubleHardValuesAllowed = null;

		//	    			nothing, ace,2,3,4,5,6,7,8,9,10,jack,queen,king
		int[] playerCards = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int[] dealerHandCards = 	{0,0,0,0,0,0,0,0,0,0,2,0,0,0};
		int[] deckCards = 			{0,1,0,0,0,0,0,0,0,0,3,0,0,0};

		//end variable choices
		VariableRankHand playerHand = new VariableRankHand();
		for(int i = 1; i <= 13; i += 1) {
			for(int j = 0; j < playerCards[i]; j += 1) {
				playerHand.addCard(i);
			}
		}

		VariableRankHand dealerHand = new VariableRankHand();
		for(int i = 1; i <= 13; i += 1) {
			for(int j = 0; j < dealerHandCards[i]; j += 1) {
				dealerHand.addCard(i);
			}
		}

		VariableRankDeck deck = new VariableRankDeck();
		for(int i = 1; i <= 13; i += 1) {
			for(int j = 0; j < deckCards[i]; j += 1) {
				deck.addCard(i);
			}
		}

		Rules rules = new Rules(hitOnSoft17, numTimesAllowedSplitting, numTimesAllowedSplittingAces,
				blackjackAfterSplittingAces, blackjackPayout, noHitSplitAces, doubleHardValuesAllowed);

		AlternatePlayerCalculator calc = new AlternatePlayerCalculator(dealerHand, deck, rules);
		
		assertEquals(blackjackPayout, calc.results(playerHand)[2], 0.00001);
	}
	
}
