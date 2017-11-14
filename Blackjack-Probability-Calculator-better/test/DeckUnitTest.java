import static org.junit.Assert.*;
import org.junit.Test;

import PlayerCalculator.ThirteenRankDeck;

public class DeckUnitTest {


	// Default constructor test
	@Test
	public void testDefaultConstructor() {
		ThirteenRankDeck deck = new ThirteenRankDeck();

		assertEquals(0, deck.numCardsInDeck());
		for(int rank = 1; rank <= 13; rank += 1) {
			assertEquals(0, deck.numCard(rank));
		}
	}

	// Single integer constructor tests
	@Test
	public void testSingleIntConstructorZero() {
		ThirteenRankDeck deck = new ThirteenRankDeck(0);

		assertEquals(0, deck.numCardsInDeck());
		for(int rank = 1; rank <= 13; rank += 1) {
			assertEquals(0, deck.numCard(rank));
		}
	}

	@Test
	public void testSingleIntConstructorOne() {
		ThirteenRankDeck deck = new ThirteenRankDeck(1);

		assertEquals(52, deck.numCardsInDeck());
		for(int rank = 1; rank <= 13; rank += 1) {
			assertEquals(4, deck.numCard(rank));
		}
	}

	@Test
	public void testSingleIntConstructorTwo() {
		ThirteenRankDeck deck = new ThirteenRankDeck(2);

		assertEquals(104, deck.numCardsInDeck());
		for(int rank = 1; rank <= 13; rank += 1) {
			assertEquals(8, deck.numCard(rank));
		}
	}

	@Test
	public void testSingleIntConstructorTwenty() {
		ThirteenRankDeck deck = new ThirteenRankDeck(20);

		assertEquals(1040, deck.numCardsInDeck());
		for(int rank = 1; rank <= 13; rank += 1) {
			assertEquals(80, deck.numCard(rank));
		}
	}

	// Draw probability tests
	@Test
	public void testDrawProbabilityNewDeck() {
		ThirteenRankDeck deck = new ThirteenRankDeck(1);
		for(int i = 1; i <= 13; i += 1) {
			assertEquals(1.0/13, deck.drawProbability(i), 0.00001);
		}
	}

	@Test
	public void testDrawProbabilityOneCardRemoved() {
		ThirteenRankDeck deck = new ThirteenRankDeck(1);
		for(int removed = 1; removed <= 13; removed += 1) {
			deck.removeCard(removed);
			for(int i = 1; i <= 13; i += 1) {
				if(i == removed) {
					assertEquals(3.0/51, deck.drawProbability(i), 0.00001);
				}else {
					assertEquals(4.0/51, deck.drawProbability(i), 0.00001);
				}
			}
			deck.addCard(removed);
		}
	}

	@Test
	public void testDrawProbabilityOneCardInDeck() {
		ThirteenRankDeck deck = new ThirteenRankDeck(0);
		for(int added = 1; added <= 13; added += 1) {
			deck.addCard(added);
			for(int i = 1; i <= 13; i += 1) {
				if(i == added) {
					assertEquals(1.0, deck.drawProbability(i), 0.00001);
				}else {
					assertEquals(0.0, deck.drawProbability(i), 0.00001);
				}
			}
			deck.removeCard(added);
		}
	}

	@Test
	public void testDrawProbabilityNoCardsInDeck() {
		ThirteenRankDeck deck = new ThirteenRankDeck(0);
		for(int i = 1; i <= 13; i += 1) {
			assertEquals(0.0, deck.drawProbability(i), 0.00001);
		}
	}

	@Test
	public void testDrawProbabilityNoCardRankInDeck() {
		ThirteenRankDeck deck = new ThirteenRankDeck(1);
		for(int removed = 1; removed <= 13; removed += 1) {
			for(int i = 0; i < 4; i += 1) {deck.removeCard(removed);}
			for(int i = 1; i <= 13; i += 1) {
				if(i == removed) {
					assertEquals(0.0, deck.drawProbability(i), 0.00001);
				}else {
					assertEquals(4.0/48, deck.drawProbability(i), 0.00001);
				}
			}
			for(int i = 0; i < 4; i += 1) {deck.addCard(removed);}
		}
	}

	// addCard tests
	@Test
	public void testAddCardBasic() {
		ThirteenRankDeck deck = new ThirteenRankDeck(0);
		for(int i = 1; i <= 13; i += 1) {
			deck.addCard(i);
			assertEquals(i, deck.numCardsInDeck());
			for(int j = 1; j <= i; j += 1) {
				assertEquals(1, deck.numCard(j));
			}
			for(int j = i+1; j <= 13; j += 1) {
				assertEquals(0, deck.numCard(j));
			}
		}
	}

	// removeCard tests
	@Test
	public void testRemoveCardBasic() {
		ThirteenRankDeck deck = new ThirteenRankDeck(1);
		for(int i = 1; i <= 13; i += 1) {
			deck.removeCard(i);
			assertEquals(52-i, deck.numCardsInDeck());
			for(int j = 1; j <= i; j += 1) {
				assertEquals(3, deck.numCard(j));
			}
			for(int j = i+1; j <= 13; j += 1) {
				assertEquals(4, deck.numCard(j));
			}
		}
	}

	// removeRandomCard tests
	@Test
	public void removeRandomCardTestEmptiesDeck() {
		ThirteenRankDeck deck = new ThirteenRankDeck(1);
		while(deck.numCardsInDeck() > 0) {
			deck.removeRandomCard();
		}
	}

	@Test
	public void removeRandomCardTestRemovesExistingCard() {
		ThirteenRankDeck deck = new ThirteenRankDeck(0);
		deck.addCard(1);
		deck.addCard(7);
		int removed = deck.removeRandomCard();
		if(removed == 1) {
			assertEquals(7, deck.removeRandomCard());
		}else if(removed == 7) {
			assertEquals(1, deck.removeRandomCard());
		}else {
			fail();
		}
	}

	// numCard tests (not really necessary)
	@Test
	public void numCardTest() {
		ThirteenRankDeck deck = new ThirteenRankDeck(10);
		for(int i = 1; i <= 13; i += 1) {
			assertEquals(40, deck.numCard(i));
		}
	}



}
