package PlayerCalculator;

import java.util.Queue;

import DealerCalculator.*;
import General.Rules;

public class PlayerCalculator {

	private DealerProbabilityCalculator dealerCalculator;
	private Rules rules;
	private TenRankDeck baseDeck10;
	private ThirteenRankDeck baseDeck13;
	private int currentIteration;
	private Queue<Node> allNodes;
	private Node startingNode;
	
	private class Node {

		public double moneyMadeFromStaying;
		public double moneyMadeFromHitting;
		public double moneyMadeFromDoubling;
		public double moneyMadeFromSplitting;
		public double mostMoneyMade;
		public double probability;
		public Node[] next;
		public VariableRankHand hand;
		public int lastProbabilityUpdateIteration;
		public int lastVisitedIteration;
		
		
		public Node() {
			this.moneyMadeFromSplitting = -1000000;
			this.moneyMadeFromHitting = -1000000;
			this.moneyMadeFromDoubling = -1000000;
			this.moneyMadeFromStaying = -1000000;
			this.mostMoneyMade = -1000000;
			this.lastVisitedIteration = 0;
			this.lastProbabilityUpdateIteration = 0;
		}
		
	}
	
	private void setNewIteration() {
		if(this.currentIteration == Integer.MAX_VALUE) {
			for(int i = 0; i < this.allNodes.size(); i += 1) {
				Node next = this.allNodes.poll();
				this.allNodes.add(next);
				next.lastVisitedIteration = 0;
				next.lastProbabilityUpdateIteration = 0;
			}
			this.currentIteration = 1;
		}else {
			this.currentIteration += 1;
		}
	}
	
	private void bubbleOutProbabilities() {
		
		
	}
	
	/*
	 * creates all nodes and sets all values except money
	 */
	private void initializeAllNodes(VariableRankHand startingHand) {
		this.startingNode = new Node();
	}
	
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
