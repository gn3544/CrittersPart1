package assignment4;

public class MyCritterN extends Critter.TestCritter{
	
	boolean alreadyMoved;
	
	public MyCritterN(){
		alreadyMoved = false;
	}
	
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
	
	@Override
	public boolean fight(String opponent) {
		if (!alreadyMoved){
			run(getRandomInt(8));
			return false;
		}
		return true;
	}
	
	public String toString() {
		return "N";
	}
	
}
