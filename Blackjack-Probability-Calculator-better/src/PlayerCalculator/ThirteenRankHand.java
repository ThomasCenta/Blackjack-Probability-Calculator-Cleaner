package PlayerCalculator;

/*
 * ThirteenRankHand distinguishes between all 10-valued cards and thus has splitting capabilities
 */
public class ThirteenRankHand extends PlayerHand {

	public ThirteenRankHand(ThirteenRankHand other) {
		this.rankSplitOn = other.getRankSplitOn();
		this.numCards = other.totalNumCards();
		this.handValue = other.getHandValue();
		this.hasAce = other.getHasAce();
		this.cardsInHand = new int[14];
		for(int i = 1; i <= 13; i += 1) { this.cardsInHand[i] = other.numCardRank(i);}
	}
	
	/*
	 * returns -1 if not a pair, else returns the rank of the pair
	 */
	private int isPair() {
		if(this.numCards != 2) {return -1;}
		for(int i = 1; i <= 13; i += 1) {
			if(this.cardsInHand[i] == 2) {return i;}
		}
		return -1;
	}
	
	public ThirteenRankHand() {
		super();
		this.cardsInHand = new int[14];
	}
	

	@Override
	public int numCardRank(int rank) {
		assert rank <= 13 && rank >= 1;
		return this.cardsInHand[rank];
	}

	@Override
	public void addCard(int rank) {
		assert rank <= 13 && rank >= 1 : "invalid card rank";
		this.numCards++;
		this.cardsInHand[rank]++;

		rank = Math.min(10, rank);
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
		assert rank <= 13 && rank >= 1 : "invalid card rank";
		assert this.cardsInHand[rank] > 0 : "no cards of rank " + rank;
		this.numCards--;
		this.cardsInHand[rank]--;
		
		rank = Math.min(rank,  10);
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
	 * @requires this is a pair
	 */
	public ThirteenRankHand splitHand() {
		int rankSplitOn = this.isPair();
		assert rankSplitOn != -1;
		
		this.removeCard(rankSplitOn);
		this.rankSplitOn = rankSplitOn;
		
		return new ThirteenRankHand(this);
	}

	/*
	 * looks only at cards in this and other
	 * returns true if this is a subset of other
	 */
	public boolean isSubset(ThirteenRankHand other) {
		for(int i = 1; i <= 13; i += 1) {
			if(this.numCardRank(i) > other.numCardRank(i)) {return false;}
		}
		return true;
	}
}
