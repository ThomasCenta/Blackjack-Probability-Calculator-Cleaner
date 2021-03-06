package VariableRankDeckTests;
import static org.junit.Assert.*;
import org.junit.Test;

import PlayerCalculator.VariableRankDeck;

public class VariableRankDeckUnitTest {


	// Default constructor test
	@Test
	public void testDefaultConstructor() {
		VariableRankDeck deck = new VariableRankDeck();

		assertEquals(0, deck.numCardsInDeck());
		for(int rank = 1; rank <= 13; rank += 1) {
			assertEquals(0, deck.numCard13(rank));
		}
	}

	// Single integer constructor tests
	@Test
	public void testSingleIntConstructorZero() {
		VariableRankDeck deck = new VariableRankDeck(0);

		assertEquals(0, deck.numCardsInDeck());
		for(int rank = 1; rank <= 13; rank += 1) {
			assertEquals(0, deck.numCard13(rank));
		}
	}

	@Test
	public void testSingleIntConstructorOne() {
		VariableRankDeck deck = new VariableRankDeck(1);

		assertEquals(52, deck.numCardsInDeck());
		for(int rank = 1; rank <= 13; rank += 1) {
			assertEquals(4, deck.numCard13(rank));
		}
	}

	@Test
	public void testSingleIntConstructorTwo() {
		VariableRankDeck deck = new VariableRankDeck(2);

		assertEquals(104, deck.numCardsInDeck());
		for(int rank = 1; rank <= 13; rank += 1) {
			assertEquals(8, deck.numCard13(rank));
		}
	}

	@Test
	public void testSingleIntConstructorTwenty() {
		VariableRankDeck deck = new VariableRankDeck(20);

		assertEquals(1040, deck.numCardsInDeck());
		for(int rank = 1; rank <= 13; rank += 1) {
			assertEquals(80, deck.numCard13(rank));
		}
	}

	// Draw probability tests
	@Test
	public void testDrawProbabilityNewDeck() {
		VariableRankDeck deck = new VariableRankDeck(1);
		for(int i = 1; i <= 13; i += 1) {
			assertEquals(1.0/13, deck.drawProbability13(i), 0.00001);
		}
	}

	@Test
	public void testDrawProbabilityOneCardRemoved() {
		VariableRankDeck deck = new VariableRankDeck(1);
		for(int removed = 1; removed <= 13; removed += 1) {
			deck.removeCard(removed);
			for(int i = 1; i <= 13; i += 1) {
				if(i == removed) {
					assertEquals(3.0/51, deck.drawProbability13(i), 0.00001);
				}else {
					assertEquals(4.0/51, deck.drawProbability13(i), 0.00001);
				}
			}
			deck.addCard(removed);
		}
	}

	@Test
	public void testDrawProbabilityOneCardInDeck() {
		VariableRankDeck deck = new VariableRankDeck(0);
		for(int added = 1; added <= 13; added += 1) {
			deck.addCard(added);
			for(int i = 1; i <= 13; i += 1) {
				if(i == added) {
					assertEquals(1.0, deck.drawProbability13(i), 0.00001);
				}else {
					assertEquals(0.0, deck.drawProbability13(i), 0.00001);
				}
			}
			deck.removeCard(added);
		}
	}

	@Test
	public void testDrawProbabilityNoCardsInDeck() {
		VariableRankDeck deck = new VariableRankDeck(0);
		for(int i = 1; i <= 13; i += 1) {
			assertEquals(0.0, deck.drawProbability13(i), 0.00001);
		}
	}

	@Test
	public void testDrawProbabilityNoCardRankInDeck() {
		VariableRankDeck deck = new VariableRankDeck(1);
		for(int removed = 1; removed <= 13; removed += 1) {
			for(int i = 0; i < 4; i += 1) {deck.removeCard(removed);}
			for(int i = 1; i <= 13; i += 1) {
				if(i == removed) {
					assertEquals(0.0, deck.drawProbability13(i), 0.00001);
				}else {
					assertEquals(4.0/48, deck.drawProbability13(i), 0.00001);
				}
			}
			for(int i = 0; i < 4; i += 1) {deck.addCard(removed);}
		}
	}

	// addCard tests
	@Test
	public void testAddCardBasic() {
		VariableRankDeck deck = new VariableRankDeck(0);
		for(int i = 1; i <= 13; i += 1) {
			deck.addCard(i);
			assertEquals(i, deck.numCardsInDeck());
			for(int j = 1; j <= i; j += 1) {
				assertEquals(1, deck.numCard13(j));
			}
			for(int j = i+1; j <= 13; j += 1) {
				assertEquals(0, deck.numCard13(j));
			}
		}
	}

	// removeCard tests
	@Test
	public void testRemoveCardBasic() {
		VariableRankDeck deck = new VariableRankDeck(1);
		for(int i = 1; i <= 13; i += 1) {
			deck.removeCard(i);
			assertEquals(52-i, deck.numCardsInDeck());
			for(int j = 1; j <= i; j += 1) {
				assertEquals(3, deck.numCard13(j));
			}
			for(int j = i+1; j <= 13; j += 1) {
				assertEquals(4, deck.numCard13(j));
			}
		}
	}

	// removeRandomCard tests
	@Test
	public void removeRandomCardTestEmptiesDeck() {
		VariableRankDeck deck = new VariableRankDeck(1);
		while(deck.numCardsInDeck() > 0) {
			deck.removeRandomCard();
		}
	}

	@Test
	public void removeRandomCardTestRemovesExistingCard() {
		VariableRankDeck deck = new VariableRankDeck(0);
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
		VariableRankDeck deck = new VariableRankDeck(10);
		for(int i = 1; i <= 13; i += 1) {
			assertEquals(40, deck.numCard13(i));
		}
	}



}
