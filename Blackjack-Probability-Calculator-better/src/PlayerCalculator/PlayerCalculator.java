package PlayerCalculator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import DealerCalculator.*;
import General.Rules;

public class PlayerCalculator {

	//private static final int STAYING_CODE = 1;
	//private static final int DOUBLING_CODE = 2;
	//private static final int HITTING_CODE = 3;
	//private static final int SPLITTING_CODE = 4;
	public static final double IMPOSSIBLE_CODE = -100000;
	
	
	private DealerProbabilityCalculator dealerCalculator;
	private Rules rules;
	private VariableRankDeck baseDeck;
	private ArrayList<Queue<Node>> allNodes;
	private Node startingNode;
	private DealerHand dealerHand;

	private class Node {

		private double moneyMadeFromStaying;
		private double moneyMadeFromHitting;
		private double moneyMadeFromDoubling;
		private double moneyMadeFromSplitting;
		private double mostMoneyMade;
		//1 = staying, 2 = doubling, 3 = hitting, 4 = splitting
		//private int bestMove;
		public Node[] next;
		public VariableRankHand hand;


		public Node() {
			this.moneyMadeFromSplitting = IMPOSSIBLE_CODE;
			this.moneyMadeFromHitting = IMPOSSIBLE_CODE;
			this.moneyMadeFromDoubling = IMPOSSIBLE_CODE;
			this.moneyMadeFromStaying = IMPOSSIBLE_CODE;
			this.mostMoneyMade = IMPOSSIBLE_CODE;

			this.next = new Node[14];
		}
		
		private void setMostMoney() {
			if(this.moneyMadeFromStaying > this.moneyMadeFromHitting) {
				this.mostMoneyMade = this.moneyMadeFromStaying;
				//this.bestMove = STAYING_CODE;
			}else {
				this.mostMoneyMade = this.moneyMadeFromHitting;
				//this.bestMove = HITTING_CODE;
			}
			if(this.moneyMadeFromDoubling > this.mostMoneyMade) {
				this.mostMoneyMade = this.moneyMadeFromDoubling;
				//this.bestMove = DOUBLING_CODE;
			}
			if(this.moneyMadeFromSplitting > this.mostMoneyMade) {
				this.mostMoneyMade = this.moneyMadeFromSplitting;
				//this.bestMove = SPLITTING_CODE;
			}
		}
		
		public void setMoneyMadeFromStaying(double money) {
			this.moneyMadeFromStaying = money;
			this.setMostMoney();
		}
		
		public void setMoneyMadeFromHitting(double money) {
			this.moneyMadeFromHitting = money;
			this.setMostMoney();
		}
		
		public void setMoneyMadeFromSplitting(double money) {
			this.moneyMadeFromSplitting = money;
			this.setMostMoney();
		}
		
		public void setMoneyMadeFromDoubling(double money) {
			this.moneyMadeFromDoubling = money;
			this.setMostMoney();
		}
	}

	/*
	 * Distinguishes between all 13 ranks
	 * 
	 * @requires dealerHand != null
	 * @requires this.startingNode.hand.isSubset(dealerHand);
	 * returns null if dealerHand is not in this
	 */
	private Node getNode(VariableRankHand hand) {
		assert hand != null;
		assert this.startingNode.hand.isSubset(hand);

		Node currentNode = this.startingNode;
		for(int i = 1; i <= 13; i += 1) {
			for(int j = this.startingNode.hand.numCardRank13(i); j < hand.numCardRank13(i); j += 1) {
				currentNode = currentNode.next[i];
				if(currentNode == null) {return null;}
			}
		}
		return currentNode;
	}

	private void addNodeToThis(Node node) {
		while(this.allNodes.size() <= node.hand.totalNumCards()) {
			this.allNodes.add(new LinkedList<Node>());
		}
		this.allNodes.get(node.hand.totalNumCards()).add(node);
	}

	/*
	 * Creates a new node if none existed for desired hand, else uses existing node to attach to base
	 * returns a node if one was created, else returns null
	 */
	private Node createOrAddBranchingNode(Node base, int rankToAdd) {
		assert base != null;
		assert rankToAdd >= 1 && rankToAdd <= 13;

		VariableRankHand nextHand = new VariableRankHand(base.hand);
		nextHand.addCard(rankToAdd);

		Node toReturn = null;
		Node branchTo = this.getNode(nextHand);
		if( branchTo == null) {
			branchTo = new Node();
			branchTo.hand = nextHand;
			toReturn = branchTo;
		}
		base.next[rankToAdd] = branchTo;
		return toReturn;
	}

	/*
	 * Branches out  to all nodes possible, starting at nodes of size >= 2
	 * @requires all nodes in startingNodes have same hand size and are >= 2
	 * 
	 * adds all newly created nodes to this.allNodes (so not the starting nodes)
	 */
	private void setAllNodesGreaterThanSizeTwo(Queue<Node> startingNodes){
		Queue<Node> toExpand = startingNodes;
		while(!toExpand.isEmpty()) {
			Node next = toExpand.poll();
			for(int i = 1; i <= 10; i += 1) {
				if(this.baseDeck.drawProbability10(i, next.hand) > 0) {
					Node newNode = createOrAddBranchingNode(next, i);
					if(newNode != null) {
						toExpand.add(newNode);
						addNodeToThis(newNode);
					}
					next.next[i] = newNode;
				}
			}
			for(int i = 11; i <= 13; i += 1) {next.next[i] = next.next[10];}
		}
	}

	/*
	 * Branches out startingNode to all nodes of hand size 2 and returns queue with all hand size 2 nodes created.
	 * assumes deck does not have startingNode.hand taken out
	 * 
	 * adds all newly created nodes to this.allNodes (so not the starting node)
	 */
	private Queue<Node> setAllNodesLessThanOrEqualToSizeTwo(Node startingNode){
		if(startingNode.hand.totalNumCards() > 2) {return new LinkedList<Node>();}
		Queue<Node> toExpand = new LinkedList<Node>();
		toExpand.add(startingNode);
		Queue<Node> sizeTwo = new LinkedList<Node>();
		while(!toExpand.isEmpty()) {
			Node next = toExpand.poll();
			if(next.hand.totalNumCards() == 2) {
				sizeTwo.add(next);
				continue;
			}
			for(int i = 1; i <= 13; i += 1) {
				if(this.baseDeck.drawProbability13(i, next.hand) > 0) {
					Node newNode = createOrAddBranchingNode(next, i);
					if(newNode != null) {
						toExpand.add(newNode);
						addNodeToThis(newNode);
					}
					next.next[i] = newNode;
				}
			}
		}
		return sizeTwo;
	}

	/*
	 * Sets up the tree structure for all nodes (does not set money)
	 * deck should not have startingHand taken out of it
	 * 
	 * Assumptions of the tree structure in this:
	 * 	 The path to reach any Node is entirely mutable:
	 * 		=> node = this.startingNode.next[1].next[12].next[4] == this.startingNode.next[12].next[4].next[1]
	 * 		   and equivalent for all other permutations of 12, 4, and 1
	 * 	 When at a node with hand size >= 2, node.next[10] == next.node[11] == node.next[12] == node.next[13]
	 * 		this is because its not possible to split past 2 cards so distinguishing between rank 10's is not necessary
	 * 
	 * Note: adds branches in order of lowest branch:
	 * 	ie. branches node.next[1].next[2] will be created before node.next[2].next[1]
	 */
	private void initializeAllNodes(VariableRankHand startingHand) {
		this.startingNode = new Node();
		this.startingNode.hand = new VariableRankHand(startingHand);
		addNodeToThis(this.startingNode);
		if(startingHand.totalNumCards() < 2) {
			Queue<Node> sizeTwoNodes = setAllNodesLessThanOrEqualToSizeTwo(this.startingNode);
			setAllNodesGreaterThanSizeTwo(sizeTwoNodes);
		}else {
			Queue<Node> startingSpot = new LinkedList<Node>();
			setAllNodesGreaterThanSizeTwo(startingSpot);
		}
	}

	/*
	 * deck should have node.hand and dealerHand taken out of it
	 * @requires dealerCalculator has been set
	 */
	private void setMoneyMadeFromStaying(Node node) {
		DealerDeck deck = new DealerDeck(this.baseDeck);
		deck.takeOutHand(this.dealerHand);
		deck.takeOutHand(new DealerHand(node.hand));
		double[] dealerResults = this.dealerCalculator.dealerProbabilities(deck, this.dealerHand);
		node.setMoneyMadeFromStaying(rules.moneyMadeOnStaying(node.hand, dealerResults));
	}

	private void setAllMoneyMadeFromStaying() {
		for(int i = 0; i < this.allNodes.size(); i += 1) {
			Queue<Node> queue = this.allNodes.get(i);
			for(int j = 0; j < queue.size(); j += 1) {
				Node next = queue.poll();
				queue.add(next);
				setMoneyMadeFromStaying(next);
			}
		}
	}

	/*
	 * @requires all applicable nodes in node.next have moneyMadeFromStaying set
	 */
	private void setMoneyMadeFromDoubling(Node node) {
		if(!rules.allowedToDouble(node.hand)) {return;}
		double money = 0.0;
		for(int i = 1; i <= 13; i += 1) {
			money += this.baseDeck.drawProbability13(i, node.hand)*node.next[i].moneyMadeFromStaying;
		}
		node.setMoneyMadeFromDoubling(money);
	}

	private void setAllMoneyMadeFromDoubling() {
		for(int i = 0; i < this.allNodes.size(); i += 1) {
			Queue<Node> queue = this.allNodes.get(i);
			for(int j = 0; j < queue.size(); j += 1) {
				Node next = queue.poll();
				queue.add(next);
				setMoneyMadeFromDoubling(next);
			}
		}
	}
	
	/*
	 * Uses the most money made from all next nodes
	 */
	private void setMoneyMadeFromHitting(Node node) {
		if(!rules.playerAllowedToHit(node.hand)) {return;}
		double money = 0.0;
		for(int i = 1; i <= 13; i += 1) {
			money += this.baseDeck.drawProbability13(i, node.hand)*node.next[i].mostMoneyMade;
		}
		node.setMoneyMadeFromHitting(money);
	}
	
	private void setAllMoneyMadeFromHitting(int smallestHandSize, int largestHandSize) {
		assert largestHandSize < this.allNodes.size();
		for(int i = largestHandSize; i >= smallestHandSize; i -= 1) {
			Queue<Node> queue = this.allNodes.get(i);
			for(int j = 0; j < queue.size(); j += 1) {
				Node next = queue.poll();
				queue.add(next);
				setMoneyMadeFromHitting(next);
			}
		}
	}
	
	/*
	 * Bad approximation, replace later
	 */
	private void setMoneyMadeFromSplitting(Node node) {
		if(rules.allowedToSplit(node.hand, 0)) {
			VariableRankHand splitHand = new VariableRankHand();
			splitHand.addCard(node.hand.isPair());
			node.setMoneyMadeFromSplitting(this.getNode(splitHand).mostMoneyMade);
		}
	}
	
	private void setAllMoneyMadeFromSplitting() {
		for(int i = 1; i <= 13; i += 1) {
			VariableRankHand hand=  new VariableRankHand();
			hand.addCard(i);
			hand.addCard(i);
			this.setMoneyMadeFromSplitting(this.getNode(hand));
		}
	}
	
	/*
	 * Deck should NOT have the cards from either hand taken out of it
	 */
	public PlayerCalculator(VariableRankHand hand, VariableRankDeck deck, DealerHand dealerHand, Rules rules) {
		this.rules = rules;
		this.dealerHand = dealerHand;
		this.baseDeck = deck;
		this.dealerCalculator = new DealerProbabilityCalculator(rules, dealerHand);
		this.allNodes = new ArrayList<Queue<Node>>();
		initializeAllNodes(hand);
		setAllMoneyMadeFromStaying();
		setAllMoneyMadeFromDoubling();
		setAllMoneyMadeFromHitting(1, this.allNodes.size()-1);
		setAllMoneyMadeFromSplitting();
		setAllMoneyMadeFromHitting(0, 1);
	}
	
	/*
	 * returns money made from {staying, doubling, hitting, splitting}
	 * where this.IMPOSSIBLE_CODE => that move is not possible;
	 */
	public double[] results(VariableRankHand hand) {
		assert this.startingNode.hand.isSubset(hand);
		
		double[] results = new double[4];
		Node resultNode = this.getNode(hand);
		results[0] = resultNode.moneyMadeFromStaying;
		results[1] = resultNode.moneyMadeFromDoubling;
		results[2] = resultNode.moneyMadeFromHitting;
		results[3] = resultNode.moneyMadeFromSplitting;
		return results;
	}

}
