package PlayerCalculator;

/**
 * This is to store all the rules that are implemented on a given table.
 *
 * @author Thomas Centa
 *
 */

public class Rules {

	public static final int BLACKJACK_INDEX = 5;
	public static final int NON_BLACKJACK_TWENTY_ONE_INDEX = 4;
	
	
	private double payoutPlayerLessThanTwentyOne(VariableRankHand playerHand, double[] dealerValues) {
		double moneyMade = 0;
		for (int i = 0; i < 4; i++) {
			if (playerHand.getHandValue() > i + 17) {
				moneyMade += dealerValues[i];
			} else if (playerHand.getHandValue() < i + 17) {
				moneyMade -= dealerValues[i];
			}
		}
		moneyMade -= dealerValues[NON_BLACKJACK_TWENTY_ONE_INDEX];
		moneyMade -= dealerValues[BLACKJACK_INDEX];
		moneyMade += dealerValues[6];
		return moneyMade;
	}

	private double payoutOnPlayerNonblackjackTwentyOne(double[] dealerValues) {
		double moneyMade = 0;
		for (int i = 0; i < dealerValues.length; i++) {
			if (i == BLACKJACK_INDEX) {
				moneyMade -= dealerValues[i];
			} else if (i != NON_BLACKJACK_TWENTY_ONE_INDEX) { // this is a push
				moneyMade += dealerValues[i];
			}
		}
		return moneyMade;
	}

	private double payoutOnPlayerBlackjack(double[] dealerValues) {
		double moneyMade = 0;
		for (int i = 0; i < dealerValues.length; i++) {
			if (i != BLACKJACK_INDEX) { 
				moneyMade += this.blackjackPayout * dealerValues[i];
			}
		}
		return moneyMade;
	}
	
	private boolean hasBlackjack(VariableRankHand hand) {
		return hand.getHandValue() == 21 && hand.totalNumCards() == 2
				&& (this.blackjackAfterSplittingAces || hand.getRankSplitOn() != 0);
	}
	
	private boolean hasBlackjack(DealerHand hand) {
		return hand.getHandValue() == 21 && hand.totalNumCards() == 2;
	}
	
  private boolean hitOnSoft17;

  private int numTimesAllowedSplitting;
  private int numTimesAllowedSplittingAces;
  private boolean blackjackAfterSplittingAces;
  private double blackjackPayout;
  private boolean noHitSplitAce;
  private boolean[] doubleHardValuesAllowed;

  /*
   * null == doubleHardValuesAllowed => all values allowed
   */
  public Rules(boolean hitOnSoft17, int numTimesAllowedSplitting, int numTimesAllowedSplittingAces,
      boolean blackjackAfterSplittingAces, double blackjackPayout, boolean noHitSplitAces,
      int[] doubleHardValuesAllowed) {

    this.hitOnSoft17 = hitOnSoft17;
    this.numTimesAllowedSplitting = numTimesAllowedSplitting;
    this.numTimesAllowedSplittingAces = numTimesAllowedSplittingAces;
    this.blackjackAfterSplittingAces = blackjackAfterSplittingAces;
    this.blackjackPayout = blackjackPayout;
    this.noHitSplitAce = noHitSplitAces;
    this.doubleHardValuesAllowed = new boolean[22];
    if(doubleHardValuesAllowed != null) {
    	for (int value: doubleHardValuesAllowed) {
    		this.doubleHardValuesAllowed[value] = true;
    	}
    }else {
    	for(int i = 0; i < 22; i += 1) {
    		this.doubleHardValuesAllowed[i] = true;
    	}
    }
  }

  /**
   *
   * @param dealerHand
   *          the hand for which this determines whether or not should stay.
   * @return true if dealer stays according to hand and rules.
   */
  public boolean dealerStays(DealerHand dealerHand) {
    if (dealerHand.getHandValue() >= 18) {
      return true;
    } else if (dealerHand.getHandValue() == 17 && this.hitOnSoft17 && dealerHand.getHasAce() ) {
      return false;
    } else if (dealerHand.getHandValue() == 17) {
      return true;
    } else { // handValue < 17
      return false;
    }
  }

  /**
   * Need to look up the rules for doubling.
   *
   * @param hand
   *          hand that wants to be doubled on
   * @return true if this hand can split
   */
  public boolean allowedToDouble(VariableRankHand hand) {
    if (hand.totalNumCards() < 2) { return false;}
    if (this.noHitSplitAce && hand.getRankSplitOn() == 0) { return false;}
    if(hand.getHandValue() > 21) {return false;}
    return this.doubleHardValuesAllowed[hand.getHandValue()];
  }

  /**
   * @param playerHand
   *          hand that is staying
   * @param dealerValues
   *          an array of probabilities for the possible dealer results these go
   *          in order: 17,18,19,20, blackjack, non-natural 21, bust. This is an
   *          array that says how probable each is.
   * @requires the probabilities in dealerValues add to ~1.
   * @requires dealerValues.length == 7
   * @return the average money made by staying on a bet of $1
   */
  public double moneyMadeOnStaying(VariableRankHand playerHand, double[] dealerValues) {
    assert dealerValues.length == 7 : "dealer values has length " + dealerValues.length + ", not 7";

    double moneyMade = 0;
    if (playerHand.getHandValue() > 21) {
      moneyMade = -1;
    } else if (this.hasBlackjack(playerHand)) {
      moneyMade = payoutOnPlayerBlackjack(dealerValues);
    } else if (playerHand.getHandValue() == 21) { // non-blackjack 21
      moneyMade = payoutOnPlayerNonblackjackTwentyOne(dealerValues);
    } else { // player has hand value < 21
      moneyMade = payoutPlayerLessThanTwentyOne(playerHand, dealerValues);
    }
    return moneyMade;
  }

  /**
   * Returns whether or not the hand given can be split given the rules in this.
   *
   * @param hand
   *          the hand that is being determined whether or not it can split.
   * @param numTimesSplit
   *          the number of times the payer has already split his hand.
   * @return true if the player is allowed to split.
   */
  public boolean allowedToSplit(VariableRankHand hand, int numTimesSplit) {
    boolean splittable = false;
    if (hand.totalNumCards() == 2) {
      for (int i = 1; i <= 13; i++) {
        if (hand.numCardRank13(i) == 2) {
          if (i == VariableRankHand.ACE_RANK) {
            if (this.numTimesAllowedSplittingAces > numTimesSplit) {
              splittable = true;
            }
          } else { // not an ace
            if (this.numTimesAllowedSplitting > numTimesSplit) {
              splittable = true;
            }
          }
        }
      }
    }
    return splittable;
  }
  
  public boolean playerAllowedToHit(VariableRankHand hand) {
	  if(hand.getRankSplitOn() == 0 && hand.totalNumCards() >= 2 && this.noHitSplitAce) {
		  return false;
	  }
	  return true;
  }
  
  public boolean playerAllowedToContinue(VariableRankHand hand) {
	  if(hand.getHandValue() > 21) { return false;}
	  return true;
  }

  /**
   * getter for the number of times a player is allowed to hit non aces.
   *
   * @return the number of times a player is allowed to hit non aces.
   */
  public int numTimesAllowedToSplitNonAces() {
    return this.numTimesAllowedSplitting;
  }

  /**
   * getter for the number of times a player is allowed to hit aces.
   *
   * @return the number of times a player is allowed to hit aces.
   */
  public int numTimesAllowedToSplitAces() {
    return this.numTimesAllowedSplittingAces;
  }

  
  /*
   * @requires this.dealerStays(dealerHand)
   * 	usually this will only be called if its known the dealer is done
   * returns an array of 6 zeroes, and one 1.0 in the space this hand has
   * spaces for hand values: {17, 18, 19, 20, non-blackjack 21, blackjack, bust}
   */
  public double[] dealerResult(DealerHand dealerHand) {
	  assert this.dealerStays(dealerHand); // => dealerHandValue >= 17
	  
	  int dealerHandValue = dealerHand.getHandValue();
	  double[] toReturn = new double[7];
	  if(dealerHandValue > 21) {
		  toReturn[6] = 1.0;
	  }else if(this.hasBlackjack(dealerHand)) {
		  toReturn[5] = 1.0;
	  }else {
		  toReturn[dealerHandValue - 17] = 1.0;
	  }
	  return toReturn;
  }
  
  public double moneyMadeOnBusting() {
	  return -1;
  }
}