package PlayerCalculator;

import java.util.Random;


public class VariableRankDeck {
	/**
	 * total number of cards in the deck.
	 */
	private int numCards;

	private int[] cardsInDeck10;
	
	private int[] cardsInDeck13;


	/**
	 * Random number generator to draw random cards.
	 */
	private Random rand;

	/**
	 * Default Constructor. Makes an empty deck
	 */
	public VariableRankDeck() {
		this.cardsInDeck10 = new int[11];
		this.cardsInDeck13 = new int[14];
		this.numCards = 0;
		this.rand = new Random();
	}

	/**
	 * copy constructor.
	 * does not copy the random number generator.
	 * @Requires otherDeck != null
	 */
	public VariableRankDeck(VariableRankDeck otherDeck) {
		assert otherDeck != null;
		this.cardsInDeck10 = new int[11];
		this.numCards = otherDeck.numCardsInDeck();
		for (int i = 1; i <= 13; i++) {
			this.cardsInDeck13[i] = otherDeck.numCard13(i);
		}
		for (int i = 1; i <= 10; i++) {
			this.cardsInDeck10[i] = otherDeck.numCard10(i);
		}
		this.rand = new Random();
	}

	/**
	 * Constructor using number of decks.
	 *
	 * @param numDecks
	 *          The number of decks to create the deck with
	 * @requires numDecks >= 0
	 */
	public VariableRankDeck(int numDecks) {
		assert numDecks >= 0;
		
		this.rand = new Random();
		this.cardsInDeck10 = new int[11];
		this.cardsInDeck13 = new int[14];
		this.numCards = 52 * numDecks;
		for (int i = 1; i <= 9; i++) {
			this.cardsInDeck10[i] = 4 * numDecks;
		}
		this.cardsInDeck10[10] = 16*numDecks;
		for (int i = 1; i <= 13; i++) {
			this.cardsInDeck13[i] = 4 * numDecks;
		}
		
	}


	/*
	 * @requires 1 <= rank <= 10
	 */
	public double drawProbability10(int rank) {
		assert rank >= 1 && rank <= 10;
		if(this.numCards == 0) {return 0.0;}
		return this.cardsInDeck10[rank] * 1.0 / this.numCards;
	}
	
	/*
	 * @requires 1 <= rank <= 13
	 */
	public double drawProbability13(int rank) {
		assert rank >= 1 && rank <= 13;
		if(this.numCards == 0) {return 0.0;}
		return this.cardsInDeck13[rank] * 1.0 / this.numCards;
	}
	
	/*
	 * returns draw probability of rank AFTER hand is taken out of the deck
	 * This is just to save computation time
	 * 
	 * @requires 1 <= rank <= 13
	 * @requires hand != null
	 * @requires this has the cards in hand
	 */
	public double drawProbability13(int rank, VariableRankHand hand) {
		assert rank >= 1 && rank <= 13;
		assert hand != null;
		assert this.contains13(hand);
		
		if(this.numCards == hand.totalNumCards()) {return 0.0;}
		return (this.cardsInDeck13[rank]-hand.numCardRank13(rank))*1.0 / (this.numCards - hand.totalNumCards());
	}
	
	/*
	 * returns draw probability of rank AFTER hand is taken out of the deck
	 * This is just to save computation time
	 * 
	 * @requires 1 <= rank <= 10
	 * @requires hand != null
	 * @requires this has the cards in hand
	 */
	public double drawProbability10(int rank, VariableRankHand hand) {
		assert rank >= 1 && rank <= 10;
		assert hand != null;
		assert this.contains10(hand);
		
		if(this.numCards == hand.totalNumCards()) {return 0.0;}
		return (this.cardsInDeck10[rank]-hand.numCardRank10(rank))*1.0 / (this.numCards - hand.totalNumCards());
	}

	public void addCard(int rank) {
		assert rank >= 1 && rank <= 13;
		this.cardsInDeck13[rank]++;
		this.cardsInDeck10[Math.min(rank, 10)]++;
		this.numCards++;
	}

	/*
	 * @requires 1 <= rank <= 10
	 * @requires a card of rank rank is in the deck
	 */
	public void removeCard(int rank) {
		assert rank >= 1 && rank <= 13;
		assert this.cardsInDeck13[rank] > 0 : "No card in the deck of rank " + rank;
		this.cardsInDeck13[rank]--;
		this.cardsInDeck13[Math.min(10, rank)]--;
		this.numCards--;
	}

	/*
	 * @requires there is a card in this deck
	 */
	public int removeRandomCard() {
		assert this.numCards > 0;
		int nextCard = this.rand.nextInt(this.numCards)+1;
		int sum = 0;
		int currentIndex = 1;
		while (currentIndex <= 13) {
			sum += this.cardsInDeck13[currentIndex];
			if (sum >= nextCard) {
				this.cardsInDeck13[currentIndex]--;
				this.numCards--;
				return currentIndex;
			}
			currentIndex++;
		}
		// should never reach here, would mean a mismatch in the sum of all cards.
		assert false : "failed to find a random card in deck";
		return -1;
	}

	/*
	 * requires 1 <= rank <= 10
	 */
	public int numCard10(int rank) {
		assert rank <= 10 && rank >= 1;
		return this.cardsInDeck10[rank];
	}
	
	/*
	 * requires 1 <= rank <= 13
	 */
	public int numCard13(int rank) {
		assert rank <= 13 && rank >= 1;
		return this.cardsInDeck13[rank];
	}


	public int numCardsInDeck() {
		return this.numCards;
	}

	public String toString() {
		String toReturn = ""+this.cardsInDeck13[1];
		for (int i = 2; i <= 13; i++) {
			toReturn += " " + this.cardsInDeck13[i];
		}
		return toReturn;
	}

	public boolean equals13(VariableRankDeck otherDeck) {
		if(this.numCards != otherDeck.numCardsInDeck()) { return false;}
		for(int i = 1; i <= 13 ; i += 1) {
			if(this.cardsInDeck13[i] != otherDeck.numCard13(i)) { return false;}
		}
		return true;
	}
	
	public boolean equals10(VariableRankDeck otherDeck) {
		if(this.numCards != otherDeck.numCardsInDeck()) { return false;}
		for(int i = 1; i <= 13; i += 1) {
			if(this.cardsInDeck13[i] != otherDeck.numCard13(i)) { return false;}
		}
		return true;
	}
	
	public boolean contains13(VariableRankHand hand) {
		for(int i = 1; i <= 13; i += 1) {
			if(this.cardsInDeck13[i] < hand.numCardRank13(i)) {return false;}
		}
		return true;
	}
	
	public boolean contains10(VariableRankHand hand) {
		for(int i = 1; i <= 10; i += 1) {
			if(this.cardsInDeck10[i] < hand.numCardRank10(i)) {return false;}
		}
		return true;
	}
}
