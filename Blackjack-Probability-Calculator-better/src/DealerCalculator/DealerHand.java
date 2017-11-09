package DealerCalculator;

public class DealerHand {

	private static final int ACE_RANK = 1;

	/**
	 * The current value of the hand by blackjack rules (ie. 0 - 21, hopefully not more).
	 */
	protected int handValue;

	/**
	 * says whether or not this hand has an ace VALUED AT 11.
	 */
	protected boolean hasAce;
	
	/**
	 * An array of size 11 with each index i corresponding to the number of cards
	 * in this hand with rank i. Aces are 1, faces are 10
	 * this array will be one-based
	 */
	protected int[] cardsInHand;

	/**
	 * total number of cards in this.
	 */
	protected int numCards;
	
	/**
	   * Default constructor. Makes an empty hand.
	   */
	  public DealerHand() {
	    this.cardsInHand = new int[11];
	    this.handValue = 0;
	    this.hasAce = false;
	    this.numCards = 0;
	  }
	
	/**
	   * Copy Constructor.
	   *
	   * @param otherHand
	   *          hand to copy from.
	   */
	  public DealerHand(DealerHand otherHand) {
	    this.cardsInHand = new int[10];
	    for (int i = 0; i < 10; i++) {
	      this.cardsInHand[i] = otherHand.numCardRank(i);
	    }
	    this.handValue = otherHand.getHandValue();
	    this.hasAce = otherHand.getHasAce();
	    this.numCards = otherHand.totalNumCards();
	  }
	
	/*
	 * @requires 1 <= rank <= 10
	 */
	public int numCardRank(int rank) {
		assert rank <= 10 && rank >= 1 : "invalid card rank";
		return this.cardsInHand[rank];
	}

	/*
	 * returns the value of this hand according to blackjack rules
	 */
	public int getHandValue() {
		return this.handValue;
	}

	/*
	 * @requires 1 <= rank <= 10
	 */
	public void addCard(int rank) {
		assert rank <= 10 && rank >= 1 : "invalid card rank";
		this.numCards++;
		this.cardsInHand[rank]++;

		int numAces = 0;
		if (this.hasAce == true) {
			numAces = 1;
		}
		if (rank == ACE_RANK) {
			numAces++;
			this.handValue += 11;
		} else {
			this.handValue += rank;
		}
		while (this.handValue > 21 && numAces > 0) {
			numAces--;
			this.handValue -= 10;
		}
		this.hasAce = numAces > 0;
	}

	/*
	 * @requires 1 <= rank <= 10
	 * @requires this hand has a card of given rank
	 */
	public void removeCard(int rank) {
		assert rank <= 10 && rank >= 1 : "invalid card rank";
		assert this.cardsInHand[rank] > 0 : "no cards of rank " + rank;
		this.numCards--;
		this.cardsInHand[rank]--;

		if (this.hasAce) {
			this.handValue -= 10;
			this.hasAce = false;
		}
		this.handValue -= rank;
		if (this.handValue <= 11 && this.cardsInHand[ACE_RANK] > 0) {
			this.handValue += 10;
			this.hasAce = true;
		}
	}

	public boolean getHasAce() {
		return this.hasAce;
	}

	public int totalNumCards() {
		return this.numCards;
	}

	@Override
	public String toString() {
		String toReturn = ""+this.cardsInHand[1];
		for (int i = 2; i < this.cardsInHand.length; i++) {
			toReturn += " " + this.cardsInHand[i];
		}
		return toReturn;
	}

	/*
	 * Returns true if this' cards are a subset of the set of cards in otherHand
	 */
	public boolean isSubset(DealerHand otherHand) {
		for(int i = 1; i <= 10; i += 1) {
			if(this.cardsInHand[i] > otherHand.numCardRank(i)) {return false;}
		}
		return true;
	}
}
