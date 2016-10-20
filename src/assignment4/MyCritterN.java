package assignment4;

public class MyCritterN extends Critter.TestCritter{
	
	boolean alreadyMoved;
	
	/**
	 * Constructor that keeps track of if a MyCritterN object has moved
	 */
	public MyCritterN(){
		alreadyMoved = false;
	}
	
	/**
	 * MyCritterN object will run, walk, or reproduce randomly with equal chance
	 */
	@Override
	public void doTimeStep() {
		int random = getRandomInt(9);
		if (random < 3){
			run(getRandomInt(8));
			alreadyMoved = true;
		}
		else if (random < 6){
			walk(getRandomInt(8));
			alreadyMoved = true;
		}
		else if (random < 9){
			reproduce(new MyCritterN(), getRandomInt(8));
		}
	}
	
	/**
	 * Runs if it hasn't moved before, else fights
	 */
	@Override
	public boolean fight(String opponent) {
		if (!alreadyMoved){
			run(getRandomInt(8));
			return false;
		}
		return true;
	}
	
	/**
	 * Returns String characterization of MyCritterN
	 */
	public String toString() {
		return "N";
	}
	
}
