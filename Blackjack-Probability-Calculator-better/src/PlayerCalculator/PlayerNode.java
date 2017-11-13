package PlayerCalculator;

/*
 * This will be the base class for the nodes used in PlayerProbabilityCalculator
 * The other nodes will vary in the number of next nodes and the hands they have
 */
public class PlayerNode {

	private double moneyMadeFromStaying;
	private double moneyMadeFromHitting;
	private double moneyMadeFromDoubling;
	private double moneyMadeFromSplitting;
	private double mostMoneyMade;
	private double probability;
	private PlayerNode[] next;
	private PlayerHand hand;

	/*
	 * Sets all money values to -1000000
	 */
	public PlayerNode() {
		this.moneyMadeFromSplitting = -1000000;
		this.moneyMadeFromHitting = -1000000;
		this.moneyMadeFromDoubling = -1000000;
		this.moneyMadeFromStaying = -1000000;
		this.mostMoneyMade = -1000000;
		
	}

	public double getMostMoneyMade() {return this.mostMoneyMade;}
	public double getMoneyMadeFromHitting() {return this.moneyMadeFromHitting;}
	public double getMoneyMadeFromStaying() {return this.moneyMadeFromStaying;}
	public double getMoneyMadeFromDoubling() {return this.moneyMadeFromDoubling;}
	public double getMoneyMadeFromSpltting() {return this.moneyMadeFromSplitting;}
	public double getProbability() {return this.probability;}
	
	public void setMoneyMadeFromHitting(double money) {
		this.moneyMadeFromHitting = money;
		if(money > this.mostMoneyMade) {this.mostMoneyMade = money;}
	}
	
	public void setMoneyMadeFromStaying(double money) {
		this.moneyMadeFromStaying = money;
		if(money > this.mostMoneyMade) {this.mostMoneyMade = money;}
	}
	
	public void setMoneyMadeFromDoubling(double money) {
		this.moneyMadeFromDoubling = money;
		if(money > this.mostMoneyMade) {this.mostMoneyMade = money;}
	}
	
	public void setMoneyMadeFromSpltting(double money) {
		this.moneyMadeFromSplitting = money;
		if(money > this.mostMoneyMade) {this.mostMoneyMade = money;}
	}
	public void setProbability(double probability) {
		assert probability >= 0 && probability <= 1.0;
		this.probability = probability;
	}

}
