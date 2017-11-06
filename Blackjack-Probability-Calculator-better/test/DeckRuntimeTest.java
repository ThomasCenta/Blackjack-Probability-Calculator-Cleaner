import java.util.LinkedList;
import java.util.Queue;

public class DeckRuntimeTest {

	private static final int NUM_ITERATIONS_PER_TEST = 10000000;
	private static final double ACCEPTABLE_DEVIATION = 0.001;
	
	private static Queue<TestStruct> tests;
	
	// set up the tests array here
	private static void setup() {
		tests = new LinkedList<TestStruct>();
		
		//first 13 will be a simple removal on each rank in regular deck
		for(int rank = 1; rank <= 13; rank += 1) {
			tests.add(new TestStruct(new Deck(1), 1.0/13, rank));
		}
		
		//next test probability of removing from two different rank card deck
		Deck deck = new Deck();
		deck.addCard(3);
		deck.addCard(13);
		tests.add(new TestStruct(new Deck(deck), 0.5, 3));
		tests.add(new TestStruct(deck, 0.5, 13));
	}
	
	
	private static class TestStruct{
		public Deck deck;
		public double expectedProbability;
		public int rankToRemove;
		
		public TestStruct(Deck deck, double expected, int rank) {
			this.deck = deck;
			this.expectedProbability = expected;
			this.rankToRemove = rank;
		}
	}
	
	private static double foundRemovalProbability(Deck deck, int rankToTest) {
		int hits = 0;
		for(int i = 0; i < NUM_ITERATIONS_PER_TEST; i += 1) {
			int removed = deck.removeRandomCard();
			if(removed == rankToTest) {
				hits += 1;
			}
			deck.addCard(removed);
		}
		return hits*1.0/NUM_ITERATIONS_PER_TEST;
	}
	
	private static void runTest(TestStruct test) {
		double actual = foundRemovalProbability(test.deck, test.rankToRemove);
		double deviation = Math.abs(actual - test.expectedProbability);
		if( deviation > ACCEPTABLE_DEVIATION) {
			System.out.print("UNACCEPTABLE: ");
		}else {
			System.out.print("ACCEPTABLE: ");
		}
		System.out.println(" deviation of "+ deviation);
		
	}
	
	public static void main(String[] args) {
		setup();

		while(!tests.isEmpty()) {
			runTest(tests.poll());
		}

		
	}
}
