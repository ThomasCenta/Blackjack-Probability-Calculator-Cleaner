/**
 * This is going to be a hand with the minimal amount of data stored for the
 * simulations.
 *
 * @author Thomas
 *
 */
public class MinimalHand {

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
   * An array of size 13 with each index i corresponding to the number of cards
   * in this hand with rank i. Aces are 1, jacks are 11, queens 12, kings 13
   * this array will be one-based
   */
  protected int[] cardsInHand13;

  /**
   * An array of size 13 with each index i corresponding to the number of cards
   * in this hand with rank i. Aces are 1, faces are 10
   * this array will be one-based
   */
  protected int[] cardsInHand10;

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
  public MinimalHand() {
    this.cardsInHand10 = new int[11];
    this.cardsInHand13 = new int[14];
    this.handValue = 0;
    this.hasAce = false;
    this.numCards = 0;
    this.rankSplitOn = -1;
  }

  /**
   * Copy Constructor.
   *
   * @param otherHand
   *          hand to copy from.
   */
  public MinimalHand(MinimalHand otherHand) {
    this.cardsInHand13 = new int[13];
    this.cardsInHand10 = new int[10];
    for (int i = 0; i < 13; i++) {
      this.cardsInHand13[i] = otherHand.numCardRank13(i);
      if (i <= 9) {
        this.cardsInHand10[i] = otherHand.numCardRank10(i);
      }
    }
    this.handValue = otherHand.getHandValue();
    this.hasAce = otherHand.getHasAce();
    this.numCards = otherHand.totalNumCards();
    this.rankSplitOn = otherHand.getRankSplitOn();
  }

  /*
   * Sets this as a hand that has been split on rankSplitOn
   * @requires 1 <= rankSplitOn <= 13
   */
  public void setSplitHand(int rankSplitOn) {
	  assert rankSplitOn <= 13 && rankSplitOn >= 0;
    this.rankSplitOn = rankSplitOn;
  }

  public int getRankSplitOn() {
    return this.rankSplitOn;
  }

  /*
   * This compares the two hands based on only the cards in them, taking into account different face cards
   * @requires otherHand != null
   */
  public int compare13(MinimalHand otherHand) {
	  assert otherHand != null;
    int toReturn = 0;
    for (int i = 0; i < 13 && toReturn == 0; i++) {
      if (this.numCardRank13(i) < otherHand.numCardRank13(i)) {
        toReturn = -1;
      } else if (this.numCardRank13(i) > otherHand.numCardRank13(i)) {
        toReturn = 1;
      }
    }
    return toReturn;
  }

  /*
   * This compares the two hands based on only the cards in them, where 10 == jack == queen == king
   * @requires otherHand != null
   */
  public int compare10(MinimalHand otherHand) {
	  assert otherHand != null;
    int toReturn = 0;
    for (int i = 0; i < 10 && toReturn == 0; i++) {
      if (this.numCardRank10(i) < otherHand.numCardRank10(i)) {
        toReturn = -1;
      } else if (this.numCardRank10(i) > otherHand.numCardRank10(i)) {
        toReturn = 1;
      }
    }
    return toReturn;
  }

  /*
   * distinguishes between 10 and all face cards
   * @requires 1 <= rank <= 13
   */
  public int numCardRank13(int rank) {
    assert rank <= 13 && rank >= 1 : "invalid card rank";
    return this.cardsInHand13[rank];
  }

  /*
   * rank == 10 => return sum(#10s, #jacks, #queens, #kings)
   * @requires 1 <= rank <= 13
   */
  public int numCardRank10(int rank) {
    assert rank <= 10 && rank >= 1 : "invalid card rank";
    return this.cardsInHand10[rank];
  }

  /*
   * returns the value of this hand according to blackjack rules
   */
  public int getHandValue() {
    return this.handValue;
  }

  /*
   * @requires 1 <= rank <= 13
   */
  public void addCard(int rank) {
    assert rank <= 13 && rank >= 1 : "invalid card rank";
    this.numCards++;
    this.cardsInHand13[rank]++;
    rank = Math.min(10, rank);
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

}