package PlayerCalculator;
import java.util.Random;

/*
 * This deck always disregards differences between 10, jack, queen, king.
 */
public class DealerDeck {


	/**
	 * total number of cards in the deck.
	 */
	private int numCards;

	/**
	 * index i is number of rank i cards. Aces are rank one, 10s, jacks, queens, and kings are 10
	 */
	private int[] cardsInDeck;

	/**
	 * Random number generator to draw random cards.
	 */
	private Random rand;

	/**
	 * Default Constructor. Makes an empty deck
	 */
	public DealerDeck() {
		this.cardsInDeck = new int[11];
		this.numCards = 0;
		this.rand = new Random();
	}

	/**
	 * copy constructor.
	 * does not copy the random number generator.
	 * @Requires otherDeck != null
	 */
	public DealerDeck(DealerDeck otherDeck) {
		assert otherDeck != null;
		this.cardsInDeck = new int[11];
		this.numCards = 0;
		for (int i = 1; i <= 10; i++) {
			this.cardsInDeck[i] = otherDeck.numCard(i);
			this.numCards += otherDeck.numCard(i);
		}
		this.rand = new Random();
	}
	
	public DealerDeck(VariableRankDeck otherDeck) {
		assert otherDeck != null;
		this.cardsInDeck = new int[11];
		this.numCards = otherDeck.numCardsInDeck();
		for (int i = 1; i <= 10; i++) {
			this.cardsInDeck[i] = otherDeck.numCard10(i);
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
	public DealerDeck(int numDecks) {
		assert numDecks >= 0;
		this.cardsInDeck = new int[11];
		this.numCards = 52 * numDecks;
		for (int i = 1; i < 10; i++) {
			this.cardsInDeck[i] = 4 * numDecks;
		}
		this.cardsInDeck[10] = 16*numDecks;
		this.rand = new Random();
	}

	
	/*
	 * @requires 1 <= rank <= 10
	 */
	public double drawProbability(int rank) {
		assert rank >= 1 && rank <= 10;
		if(this.numCards == 0) {return 0.0;}
		return this.cardsInDeck[rank] * 1.0 / this.numCards;
	}
	
	public boolean contains(DealerHand hand) {
		for(int i = 1; i <= 10; i += 1) {
			if(this.cardsInDeck[i] < hand.numCardRank(i)) {return false;}
		}
		return true;
	}
	
	/*
	 * returns draw probability of rank AFTER hand is taken out of the deck
	 * This is just to save computation time
	 * 
	 * @requires 1 <= rank <= 10
	 * @requires hand != null
	 * @requires this has the cards in hand
	 */
	public double drawProbability(int rank, DealerHand hand) {
		assert rank >= 1 && rank <= 10;
		assert hand != null;
		assert this.contains(hand);
		
		if(this.numCards == hand.totalNumCards()) {return 0.0;}
		return (this.cardsInDeck[rank]-hand.numCardRank(rank))*1.0 / (this.numCards - hand.totalNumCards());
	}

	public void addCard(int rank) {
		this.cardsInDeck[rank]++;
		this.numCards++;
	}

	/*
	 * @requires 1 <= rank <= 13
	 * @requires a card of rank rank is in the deck
	 */
	public void removeCard(int rank) {
		assert rank >= 1 && rank <= 10;
		assert this.cardsInDeck[rank] > 0 : "No card in the deck of rank " + rank;
		this.cardsInDeck[rank]--;
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
		while (currentIndex <= 10) {
			sum += this.cardsInDeck[currentIndex];
			if (sum >= nextCard) {
				this.cardsInDeck[currentIndex]--;
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
	public int numCard(int rank) {
		assert rank <= 10 && rank >= 1;
		return this.cardsInDeck[rank];
	}

	public int numCardsInDeck() {
		return this.numCards;
	}

	public String toString() {
		String toReturn = ""+this.cardsInDeck[1];
		for (int i = 2; i <= 10; i++) {
			toReturn += " " + this.cardsInDeck[i];
		}
		return toReturn;
	}

	public boolean equals(DealerDeck otherDeck) {
		if(this.numCards != otherDeck.numCardsInDeck()) { return false;}
		for(int i = 1; i <= 10; i += 1) {
			if(this.cardsInDeck[i] != otherDeck.numCard(i)) { return false;}
		}
		return true;
	}
	
	/*
	 * @requires hand != null
	 * @requires this has the cards in hand
	 */
	public void takeOutHand(DealerHand hand) {
		assert hand != null;
		for(int i = 1; i <= 10; i += 1) {
			assert this.cardsInDeck[i] >= hand.numCardRank(i);
			this.cardsInDeck[i] -= hand.numCardRank(i);
		}
		this.numCards -= hand.totalNumCards();
	}
	
	/*
	 * @requires hand != null
	 * @requires this has the cards in hand
	 */
	public void takeOutHand(VariableRankHand hand) {
		assert hand != null;
		for(int i = 1; i <= 10; i += 1) {
			assert this.cardsInDeck[i] >= hand.numCardRank10(i);
			this.cardsInDeck[i] -= hand.numCardRank10(i);
		}
		this.numCards -= hand.totalNumCards();
	}
	
	/*
	 * @requires hand != null
	 */
	public void addHand(DealerHand hand) {
		assert hand != null;
		for(int i = 1; i <= 10; i += 1) {
			this.cardsInDeck[i] += hand.numCardRank(i);
		}
		this.numCards += hand.totalNumCards();
	}
	
	
}
