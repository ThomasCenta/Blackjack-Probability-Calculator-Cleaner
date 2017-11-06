import java.util.Random;

/**
 * The Implementation of the Deck class.
 *
 * @author Thomas Centa
 *
 */
public class Deck{

	/**
	 * total number of cards in the deck.
	 */
	private int numCards;

	/**
	 * index i is number of rank i cards. Aces are rank one, jacks 11, queens 12, kings 13
	 */
	private int[] cardsInDeck;

	/**
	 * Random number generator to draw random cards.
	 */
	private Random rand;

	/**
	 * Default Constructor. Makes an empty deck
	 */
	public Deck() {
		this.cardsInDeck = new int[14];
		this.numCards = 0;
		this.rand = new Random();
	}

	/**
	 * copy constructor.
	 * does not copy the random number generator.
	 * @Requires otherDeck != null
	 */
	public Deck(Deck otherDeck) {
		assert otherDeck != null;
		this.cardsInDeck = new int[14];
		this.numCards = 0;
		for (int i = 1; i <= 13; i++) {
			this.cardsInDeck[i] = otherDeck.numCard(i);
			this.numCards += otherDeck.numCard(i);
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
	public Deck(int numDecks) {
		assert numDecks >= 0;
		this.cardsInDeck = new int[14];
		this.numCards = 52 * numDecks;
		for (int i = 1; i < this.cardsInDeck.length; i++) {
			this.cardsInDeck[i] = 4 * numDecks;
		}
		this.rand = new Random();
	}

	
	/*
	 * @requires 1 <= rank <= 13
	 */
	public double drawProbability(int rank) {
		assert rank >= 1 && rank <= 13;
		if(this.numCards == 0) {return 0.0;}
		return this.cardsInDeck[rank] * 1.0 / this.numCards;
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
		assert rank >= 1 && rank <= 13;
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
		while (currentIndex <= 13) {
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
	 * requires 1 <= rank <= 13
	 */
	public int numCard(int rank) {
		assert rank <= 13 && rank >= 1;
		return this.cardsInDeck[rank];
	}

	public int numCardsInDeck() {
		return this.numCards;
	}

	/*
	 * @requires hand != null
	 */
	public void takeOutHand(MinimalHand hand) {
		assert hand != null;
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < hand.numCardRank13(i); j++) {
				this.removeCard(i); // let the kernel method take care of it.
			}
		}
	}

	/*
	 * @requires hand != null
	 */
	public void addHand(MinimalHand hand) {
		assert hand != null;
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < hand.numCardRank13(i); j++) {
				this.addCard(i); // let the kernel method take care of it.
			}
		}
	}

	public String toString() {
		String toReturn = ""+this.cardsInDeck[1];
		for (int i = 2; i <= 13; i++) {
			toReturn += " " + this.cardsInDeck[i];
		}
		return toReturn;
	}

}
