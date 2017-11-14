package PlayerCalculator;
import java.util.Random;


/**
 *
 * @author Thomas Centa
 *
 */
public class TenRankDeck{

	/**
	 * total number of cards in the deck.
	 */
	private int numCards;

	private int[] cardsInDeck;
	

	/**
	 * Random number generator to draw random cards.
	 */
	private Random rand;

	/**
	 * Default Constructor. Makes an empty deck
	 */
	public TenRankDeck() {
		this.cardsInDeck = new int[11];
		this.numCards = 0;
		this.rand = new Random();
	}

	/**
	 * copy constructor.
	 * does not copy the random number generator.
	 * @Requires otherDeck != null
	 */
	public TenRankDeck(TenRankDeck otherDeck) {
		assert otherDeck != null;
		this.cardsInDeck = new int[11];
		this.numCards = otherDeck.numCardsInDeck();
		for (int i = 1; i <= 10; i++) {
			this.cardsInDeck[i] = otherDeck.numCard(i);
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
	public TenRankDeck(int numDecks) {
		assert numDecks >= 0;
		this.cardsInDeck = new int[11];
		this.numCards = 52 * numDecks;
		for (int i = 1; i <= 9; i++) {
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

	public void addCard(int rank) {
		this.cardsInDeck[rank]++;
		this.numCards++;
	}

	/*
	 * @requires 1 <= rank <= 10
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

	public boolean equals(TenRankDeck otherDeck) {
		if(this.numCards != otherDeck.numCardsInDeck()) { return false;}
		for(int i = 1; i <= 10 ; i += 1) {
			if(this.cardsInDeck[i] != otherDeck.numCard(i)) { return false;}
		}
		return true;
	}
}
