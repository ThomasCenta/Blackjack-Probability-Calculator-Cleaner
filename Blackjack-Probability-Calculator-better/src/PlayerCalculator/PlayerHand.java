package PlayerCalculator;
/**
 * This is going to be a hand with the minimal amount of data stored for the
 * simulations.
 *
 * @author Thomas Centa
 *
 */
public abstract class PlayerHand {

  protected static final int ACE_RANK = 1;

/**
   * The current value of the hand by blackjack rules (ie. 0 - 21, hopefully not more).
   */
  protected int handValue;

  /**
   * says whether or not this hand has an ace valued at 11.
   */
  protected boolean hasAce;

  /*
   * array of number of cards in hand. this.cardsInHand[0] == number of aces in this
   */
  protected int[] cardsInHand;


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
  public PlayerHand() {
    this.handValue = 0;
    this.hasAce = false;
    this.numCards = 0;
    this.rankSplitOn = -1;
  }
  
  public abstract int numCardRank(int rank);
  public abstract void addCard(int rank);
  public abstract void removeCard(int rank);
  
  public int getRankSplitOn() {return this.rankSplitOn;}
  public int getHandValue() {return this.handValue;}
  public boolean getHasAce() {return this.hasAce;}
  public int totalNumCards() {return this.numCards;}

  @Override
  public String toString() {
    String toReturn = "";
    for (int i = 1; i < this.cardsInHand.length; i++) {
      toReturn += this.cardsInHand[i] + " ";
    }
    return toReturn;
  }

}