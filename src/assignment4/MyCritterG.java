package assignment4;

public class MyCritterG extends Critter.TestCritter{
	
	int dir;
	
	/**
	 * Gets a random direction variable
	 */
	public MyCritterG(){
		dir = getRandomInt(8);
	}
	
	/**
	 * Will run/walk if energy permits, or does nothing
	 */
	@Override
	public void doTimeStep() {
		if (getEnergy() > Params.run_energy_cost){
			run(dir);
		}
		else if (getEnergy() > Params.walk_energy_cost){
			walk(dir);
		}
	}
	
	/**
	 * Fights if opponent is Algae, if not walks away if energy permit or tries to reproduce
	 */
	@Override
	public boolean fight(String opponent) {
		if (opponent.equals("@")){
			return true;
		}
		else if (getEnergy() > Params.walk_energy_cost){
			walk(dir);
			return false;
		}
		reproduce(new MyCritterG(), getRandomInt(8));
		return false;
	}
	
	/**
	 * Returns String characterization of MyCritterG
	 */
	public String toString() {
		return "G";
	}
	
}
