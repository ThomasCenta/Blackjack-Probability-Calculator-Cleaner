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
	/*
	 * rank == 10 => return sum(#10s, #jacks, #queens, #kings)
	 * @requires 1 <= rank <= 13
	 */
	public int numCardRank10(int rank) {
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
		this.cardsInHand10[rank]++;

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
	 * @requires 1 <= rank <= 13
	 * @requires this hand has a card of given rank
	 */
	public void removeCard(int rank) {
		assert rank <= 13 && rank >= 1 : "invalid card rank";
		assert this.cardsInHand13[rank] > 0 : "no cards of rank " + rank;
		this.numCards--;
		this.cardsInHand13[rank]--;
		rank = Math.min(10, rank);
		this.cardsInHand10[rank]--;

		if (this.hasAce) {
			this.handValue -= 10;
			this.hasAce = false;
		}
		this.handValue -= rank;
		if (this.handValue <= 11 && this.cardsInHand10[ACE_RANK] > 0) {
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
		String toReturn = "";
		for (int i = 1; i < 13; i++) {
			toReturn += this.cardsInHand13[i] + " ";
		}
		toReturn += this.cardsInHand13[13];
		return toReturn;
	}

	/*
	 * Splits this hand and returns the new hand
	 * @requires the hand be a pair
	 */
	public MinimalHand splitHand() {
		assert this.numCards == 2 : "splitting hand has " + this.numCards + " cards";
		MinimalHand newHand = null;
		// now find the splitting rank (the one with two cards).
		for (int i = 1; i <= 13; i++) {
			if (this.cardsInHand13[i] == 2) {
				newHand = new MinimalHand();
				newHand.addCard(i);
				newHand.setSplitHand(i);
				this.rankSplitOn = i;
				this.removeCard(i); // take one of this rank out.
			}
		}
		assert newHand != null : "pair not found in splitting hand.";
		return newHand;
	}

	/*
	 * Returns true if this' cards are a subset of the set of cards in otherHand
	 * assumes all 10s, jacks, queens, kings are equivalent
	 */
	public boolean isSubset10(MinimalHand otherHand) {
		for(int i = 1; i <= 10; i += 1) {
			if(this.cardsInHand10[i] > otherHand.numCardRank10(i)) {return false;}
		}
		return true;
	}
}
