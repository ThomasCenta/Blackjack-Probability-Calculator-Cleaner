package PlayerCalculatorTests;

import PlayerCalculator.VariableRankDeck;
import PlayerCalculator.VariableRankHand;

public class AlternatePlayerCalculatorSimulationTest {

	private static void setup() {
		//					nothing, ace,2,3,4,5,6,7,8,9,10,jack,queen,king
		int[] playerCards = 		{0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int[] dealerHandCards = 	{0,0,0,0,0,0,0,0,0,0,2,0,0,0};
		int[] deckCards = 			{0,1,0,0,0,0,0,0,0,0,3,0,0,0};
		
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
	}
	
	private static VariableRankHand dealerhand;
	private static VariableRankHand playerHand;
	private static VariableRankDeck deck;
	private static AlternatePlayerCalculator calc;
	
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
	public static double runSimulationCalculateDealer(VariableRankHand hand, VariableRankDeck deck) {
		int bestMove = largestIndex(calc.results(hand));
		if(bestMove == AlternatePlayerCalculator.STAYING_INDEX) {
			
		}else if(bestMove == AlternatePlayerCalculator.DOUBLING_INDEX) {
			
		}else if(bestMove == AlternatePlayerCalculator.HITTING_INDEX) {
			
		}else {
			assert false;
		}
		return 0;
	}
	
	
	public static void main(String[] args) {
		calc = 
	}
}
