package PlayerCalculator;

import DealerCalculator.*;
import General.Rules;

public class PlayerCalculator {

	private DealerProbabilityCalculator dealerCalculator;
	private Rules rules;
	
	private TenRankDeck baseDeck10;
	private ThirteenRankDeck baseDeck13;
	
	private class Node {

		public double moneyMadeFromStaying;
		public double moneyMadeFromHitting;
		public double moneyMadeFromDoubling;
		public double moneyMadeFromSplitting;
		public double mostMoneyMade;
		public double probability;
		public Node[] next;
		public PlayerHand hand;
		
		/*
		 * Sets all money values to -1000000
		 */
		public Node() {
			this.moneyMadeFromSplitting = -1000000;
			this.moneyMadeFromHitting = -1000000;
			this.moneyMadeFromDoubling = -1000000;
			this.moneyMadeFromStaying = -1000000;
			this.mostMoneyMade = -1000000;
		}
		
	}
	
	private void bubbleOutProbabilities() {}
	
	/*
	 * deck should have node.hand and dealerHand taken out of it
	 * @requires dealerCalculator has been set
	 */
	private void setMoneyMadeFromStaying(Node node, DealerDeck deck, DealerHand dealerHand) {
		double[] dealerResults = this.dealerCalculator.dealerProbabilities(deck, dealerHand);
		node.moneyMadeFromStaying = rules.moneyMadeOnStaying(node.hand, dealerResults);
	}
	
	public PlayerCalculator() {}
	
}
