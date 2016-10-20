/* CRITTERS CritterM.java
 * EE422C Project 4 submission by
 * <Gaurav Nagar>
 * <gn3544>
 * <16480>
 * <Minkoo Park>
 * <mp32454>
 * <16480>
 * Slip days used: <0>
 * Fall 2016
 */

/*
 * CritterM is represented by a chracter 'M.' CritterM always fights Algae to gain additonal energy
 * but they don't always fight other Critters. They decide to fight another Critter if they get an even
 * random number. They decide to runaway in random direction if they get an odd ramdom number 
 * (nubers range from 0 to 7). CritterM does not reproduce but it moves in random directions by 6 spots
 * in randome directions, 2 at a time. to encounter Algae more easily. They survice by eating Algae to 
 * obtain extra energy. If no energy, they die. Since no CritterM reproduces, they eventually go distinct 
 * if no new CritterM are palced.
 */

package assignment4;

import assignment4.Critter.TestCritter;

public class CritterM extends TestCritter {
	
	@Override
	public void doTimeStep() {
		int dir = Critter.getRandomInt(8);
		run(dir);
		run(dir);
		run(dir);
	}
	
	@Override
	public boolean fight(String type) { 
		if(type == "@"){
			return true;
		}
		else{
			int num = Critter.getRandomInt(8);
			if(num % 2 == 0){
				return true;
			}
		run(getRandomInt(8));
		return false;
		}
	}
	
	@Override
	public String toString() { return "M"; }
	

	
}
