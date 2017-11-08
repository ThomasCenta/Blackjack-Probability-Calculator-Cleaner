import java.util.LinkedList;
import java.util.Queue;

/*
 * This will be a class creating a tree of hand nodes useful for doing probability calculations
 */
public class DealerHandTree {


	private static class Node{
		public MinimalHand hand;
		public double probability;
		/*
		 * next should have length 11, if set, and each index corresponds to the rank added to this to get next
		 * The zero index should be left null
		 */
		public Node[] next;
		public double[] dealerResult;
		public int latestIteration;

		public Node() {
			this.dealerResult = null;
			this.hand = null;
			this.next = null;
			this.probability = 0.0;
			this.latestIteration = 0;
		}
		public Node(MinimalHand hand) {
			this.hand = hand;
			this.dealerResult = null;
			this.next = null;
			this.latestIteration = 0;
			this.probability = 0.0;
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
			Node next = new Node(new MinimalHand(node.hand));
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
	private void initializeAllNodes(MinimalHand startingHand) {
		this.startingNode = new Node();
		this.startingNode.hand = new MinimalHand(startingHand);

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
				next.latestIteration = 0;
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
	public DealerHandTree(Rules rules, MinimalHand startingHand) {
		assert rules != null;
		assert startingHand != null;
		
		this.rules = rules;
		initializeAllNodes(startingHand);
	}

	/*
	 * @requires deck != null
	 * @requires dealerHand != null
	 * @requires this.startingNode.hand.isSubset10(dealerHand)
	 */
	public double[] dealerProbabilities(Deck deck, MinimalHand dealerHand) {
		assert deck != null;
		assert dealerHand != null;
		assert this.startingNode.hand.isSubset10(dealerHand);
		
		if(!this.rules.dealerStays(dealerHand)) {
			return this.rules.dealerResult(dealerHand);
		}
		
		return null; //fix this 
		
	}
	

}
