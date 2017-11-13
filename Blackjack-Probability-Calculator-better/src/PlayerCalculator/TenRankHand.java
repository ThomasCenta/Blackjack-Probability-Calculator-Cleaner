package PlayerCalculator;

public class TenRankHand extends PlayerHand{
	
	
	public TenRankHand(TenRankHand other) {
		this.rankSplitOn = other.getRankSplitOn();
		this.numCards = other.totalNumCards();
		this.handValue = other.getHandValue();
		this.hasAce = other.getHasAce();
		this.cardsInHand = new int[11];
		for(int i = 1; i <= 10; i += 1) { this.cardsInHand[i] = other.numCardRank(i);}
	}
	
	public TenRankHand() {
		super();
		this.cardsInHand = new int[11];
	}
	

	@Override
	public int numCardRank(int rank) {
		assert rank <= 10 && rank >= 1;
		return this.cardsInHand[rank];
	}

	@Override
	public void addCard(int rank) {
		assert rank <= 10 && rank >= 1 : "invalid card rank";
		this.numCards++;
		this.cardsInHand[rank]++;
		
		int numAces = 0;
		if (this.hasAce) {
			numAces = 1;
		}
		if (rank == PlayerHand.ACE_RANK) {
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

	@Override
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

	/*
	 * looks only at cards in this and other
	 * returns true if this is a subset of other
	 */
	public boolean isSubset(TenRankHand other) {
		for(int i = 1; i <= 10; i += 1) {
			if(this.numCardRank(i) > other.numCardRank(i)) {return false;}
		}
		return true;
	}
}
