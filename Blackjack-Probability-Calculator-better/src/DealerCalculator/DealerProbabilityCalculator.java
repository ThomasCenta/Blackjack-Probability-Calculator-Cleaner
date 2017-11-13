package DealerCalculator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import General.Rules;
import PlayerCalculator.PlayerDeck;

/*
 * This will be a class creating a tree of hand nodes useful for doing probability calculations
 */
public class DealerProbabilityCalculator {


	private static class Node{
		public DealerHand hand;
		public double probability;
		/*
		 * next should have length 11, if set, and each index corresponds to the rank added to this to get next
		 * The zero index should be left null
		 */
		public Node[] next;
		public double[] dealerResult;
		public int lastProbabilityUpdateIteration;
		public int lastVisitedIteration;

		public Node() {
			this.dealerResult = null;
			this.hand = null;
			this.next = new Node[11];
			this.probability = 0.0;
			this.lastVisitedIteration = 0;
			this.lastProbabilityUpdateIteration = 0;
		}
		public Node(DealerHand hand) {
			this.hand = hand;
			this.dealerResult = null;
			this.next = new Node[11];
			this.lastVisitedIteration = 0;
			this.lastProbabilityUpdateIteration = 0;
			this.probability = 0.0;
		}

		@Override
		public String toString() {
			StringBuilder str = new StringBuilder();
			str.append("hand: "+hand.toString());
			return str.toString();
		}
	}


	private Node startingNode;

	private Queue<Node> allNodes;

	private Rules rules;

	private int currentIteration;

	/*
	 * @requires this.rules != null
	 * finds all next nodes 
	 * 		adds these to node.next and then returns them
	 */
	private Queue<Node> setMiddleNode(Node node){
		Queue<Node> toReturn = new LinkedList<Node>();
		node.next = new Node[11];
		for(int i = 1; i <= 10; i += 1) {
			Node next = new Node(new DealerHand(node.hand));
			next.hand.addCard(i);
			toReturn.add(next);
			node.next[i] = next;
		}
		return toReturn;
	}

	/*
	 * returns this.rules.dealerStays(node)
	 * if node is a stay hand, set dealerResults
	 */
	private boolean setLeafNode(Node node) {
		if(this.rules.dealerStays(node.hand)) {
			node.dealerResult = this.rules.dealerResult(node.hand);
			return true;
		}else {
			return false;
		}
	}

	/*
	 * @requires this.rules != null
	 */
	private void initializeAllNodes(DealerHand startingHand) {
		this.startingNode = new Node();
		this.startingNode.hand = new DealerHand(startingHand);

		this.allNodes = new LinkedList<Node>();
		Queue<Node> toAdd = new LinkedList<Node>();
		toAdd.add(this.startingNode);
		while(!toAdd.isEmpty()) {
			Node next = toAdd.poll();
			this.allNodes.add(next);
			if(!setLeafNode(next)) {
				Queue<Node> nextChildren = setMiddleNode(next);
				while(!nextChildren.isEmpty()) {
					toAdd.add(nextChildren.poll());
				}
			}
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

	/*
	 * Will create a tree starting with the startingHand and branching into all possible hands
	 * @requires rules != null
	 * @requires startingHand != null
	 */
	public DealerProbabilityCalculator(Rules rules, DealerHand startingHand) {
		assert rules != null;
		assert startingHand != null;

		this.rules = rules;
		initializeAllNodes(startingHand);
	}


	/*
	 * @requires dealerHand != null
	 * @requires this.startingNode.hand.isSubset10(dealerHand);
	 * returns null if dealerHand is not in this
	 */
	private Node getNode(DealerHand dealerHand) {
		assert dealerHand != null;
		assert this.startingNode.hand.isSubset(dealerHand);

		Node currentNode = this.startingNode;
		for(int i = 1; i <= 10; i += 1) {
			for(int j = this.startingNode.hand.numCardRank(i); j < dealerHand.numCardRank(i); j += 1) {
				currentNode = currentNode.next[i];
				if(currentNode == null) {return null;}
			}
		}
		return currentNode;
	}

	/*
	 * deck is base deck WITHOUT node.hand taken out of it
	 */
	private void updateProbabilitiesOfNextLayer(Node node, DealerDeck deck) {
		for(int i = 1; i <= 10; i += 1) {
			if(node.next[i].lastProbabilityUpdateIteration != currentIteration) {
				node.next[i].probability = 0;
				node.next[i].lastProbabilityUpdateIteration = currentIteration;
			}
			node.next[i].probability += node.probability*deck.drawProbability(i, node.hand);
		}
	}

	private double sum(double[] arr) {
		double sum = 0.0;
		for(int i = 0; i < arr.length; i += 1) {
			sum += arr[i];
		}
		return sum;
	}

	/*
	 * sets a new iteration, then bubbles out probabilities
	 * sets node to 1.0, then each subsequent to the probability of it occurring.
	 * returns a Queue with all leaf nodes visited.
	 * 
	 * deck is the deck when at node (so without node.hand)
	 */
	private Queue<Node> bubbleOutProbabilities(DealerDeck deck, Node node){
		assert node != null;

		DealerDeck deckWithNodeHand = new DealerDeck(deck);
		deckWithNodeHand.addHand(node.hand);
		setNewIteration();
		Queue<Node> leavesVisited = new LinkedList<Node>();
		Queue<Node> toProcess = new LinkedList<Node>();
		toProcess.add(node);
		while(!toProcess.isEmpty()) {
			Node next = toProcess.poll();
			if(next.lastVisitedIteration == this.currentIteration) {continue;}
			next.lastVisitedIteration = this.currentIteration;
			if(next.next[1] != null) {
				updateProbabilitiesOfNextLayer(next, deckWithNodeHand);
				for(int i = 1; i <= 10; i += 1) {
					if(deckWithNodeHand.drawProbability(i, next.hand) > 0) {
						toProcess.add(next.next[i]);
					}
				}
			}else {
				leavesVisited.add(next);
			}
		}
		return leavesVisited;
	}

	/*
	 * @requires deck != null
	 * @requires dealerHand != null
	 * @requires this.startingNode.hand.isSubset(dealerHand)
	 * 
	 * deck is assumed to have the dealerHand taken out of it (ie. it is not the initial deck of the game)
	 */
	public double[] dealerProbabilities(DealerDeck deck, DealerHand dealerHand) {
		assert deck != null;

		if(this.rules.dealerStays(dealerHand)) {
			return this.rules.dealerResult(dealerHand);
		}
		Node startNode = this.getNode(dealerHand);
		assert startNode != null;
		startNode.probability = 1.0;

		Queue<Node> reachableLeaves = bubbleOutProbabilities(deck, startNode);

		double[] dealerResults = new double[7];
		while(!reachableLeaves.isEmpty()) {
			Node next = reachableLeaves.poll();
			for(int i = 0; i < 7; i += 1) {
				dealerResults[i] += next.probability*next.dealerResult[i];
			}
		}
		//assert Math.abs(sum(dealerResults)-1) < 0.0001;
		return dealerResults;
	}


}
