package assignment4;

public class MyCritterG extends Critter.TestCritter{
	
	int dir;
	
	public MyCritterG(){
		dir = getRandomInt(8);
	}
	
	@Override
	public void doTimeStep() {
		if (getEnergy() > Params.run_energy_cost){
			run(dir);
		}
		else if (getEnergy() > Params.walk_energy_cost){
			walk(dir);
		}
	}
	
	@Override
	public boolean fight(String opponent) {
		if (opponent.equals("Algae")){
			return true;
		}
		else if (getEnergy() > Params.walk_energy_cost){
			walk(dir);
			return false;
		}
		reproduce(new MyCritterG(), getRandomInt(8));
		return false;
	}
	
	public String toString() {
		return "G";
	}
	
}
