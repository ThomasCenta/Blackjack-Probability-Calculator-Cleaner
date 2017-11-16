package PlayerCalculator;

public class VariableRankHand {

	public static final int ACE_RANK = 1;

	/**
	 * The current value of the hand by blackjack rules (ie. 0 - 21, hopefully not more).
	 */
	protected int handValue;

	/**
	 * says whether or not this hand has an ace valued at 11.
	 */
	protected boolean hasAce;

	protected int[] cardsInHand10;

	protected int[] cardsInHand13;

	/**
	 * total number of cards in this.
	 */
	protected int numCards;

	/**
	 * the rank this hand has been split on. -1 if not split.
	 */
	protected int rankSplitOn;

	/**
	 * Default constructor. Makes an empty hand.
	 */
	public VariableRankHand() {
		this.handValue = 0;
		this.hasAce = false;
		this.numCards = 0;
		this.rankSplitOn = -1;
		this.cardsInHand10 = new int[11];
		this.cardsInHand13 = new int[14];
	}
	
	public VariableRankHand(VariableRankHand other) {
		this.handValue = other.getHandValue();
		this.hasAce = other.hasAce;
		this.numCards = other.totalNumCards();
		this.rankSplitOn = other.getRankSplitOn();
		this.cardsInHand10 = new int[11];
		this.cardsInHand13 = new int[14];
		for(int i = 1; i <= 13; i += 1) {
			this.cardsInHand13[i] = other.numCardRank13(i);
		}
		for(int i = 1; i <= 10; i += 1) {
			this.cardsInHand10[i] = other.numCardRank10(i);
		}
	}

	public  int numCardRank10(int rank) {
		assert rank <= 10 && rank >= 1;
		return this.cardsInHand10[rank];
	}

	public  int numCardRank13(int rank) {
		assert rank <= 13 && rank >= 1;
		return this.cardsInHand13[rank];
	}

	public void addCard(int rank) {
		assert rank <= 13 && rank >= 1 : "invalid card rank";
		this.numCards++;
		this.cardsInHand13[rank]++;
		rank = Math.min(10,  rank);
		this.cardsInHand10[rank]++;

		int numAces = 0;
		if (this.hasAce) {
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


	public void removeCard(int rank) {
		assert rank <= 13 && rank >= 1 : "invalid card rank";
		assert this.cardsInHand13[rank] > 0 : "no cards of rank " + rank;
		this.numCards--;
		this.cardsInHand13[rank]--;
		rank = Math.min(10,  rank);
		this.cardsInHand10[rank]--;

		if (this.hasAce) {
			this.handValue -= 10;
			this.hasAce = false;
		}
		this.handValue -= rank;
		if (this.handValue <= 11 && this.cardsInHand13[ACE_RANK] > 0) {
			this.handValue += 10;
			this.hasAce = true;
		}
	}

	public int getRankSplitOn() {return this.rankSplitOn;}
	public int getHandValue() {return this.handValue;}
	public boolean getHasAce() {return this.hasAce;}
	public int totalNumCards() {return this.numCards;}
	
	/*
	 * looks only at cards in this and other
	 * returns true if this is a subset of other
	 */
	public boolean isSubset(VariableRankHand other) {
		for(int i = 1; i <= 13; i += 1) {
			if(this.numCardRank13(i) > other.numCardRank13(i)) {return false;}
		}
		return true;
	}
	
	/*
	 * returns -1 if not a pair, the rank of the pair otherwise
	 */
	public int isPair() {
		if(this.totalNumCards() != 2) { return -1;}
		for(int i = 1; i <= 13; i += 1) {
			if(this.cardsInHand13[i] == 2) {return i;}
		}
		return -1;
	}

	@Override
	public String toString() {
		String toReturn = "";
		for (int i = 1; i < this.cardsInHand13.length; i++) {
			toReturn += this.cardsInHand13[i] + " ";
		}
		return toReturn;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof VariableRankHand)) { return false;}
		VariableRankHand hand = (VariableRankHand) o;
		for(int i = 1; i <= 13; i += 1) {
			if(this.numCardRank13(i) != hand.numCardRank13(i)) {return false;}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int sum = 0;
		for(int i = 1; i <= 13; i += 1) {
			sum += i*i*this.cardsInHand13[i]*this.cardsInHand13[i];
		}
		return sum;
	}
}
