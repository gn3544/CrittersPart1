/* CRITTERS CritterK.java
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
 * CritterK is represented by a chracter 'K.' CritterM always fights Algae to gain additonal energy
 * but they don't always fight other Critters. They decide to fight another Critter if they get an odd
 * random number. They decide to runaway in random direction if they get an even ramdom number 
 * (nubers range from 0 to 7). CritterK behaves very simply! They always walk to the right and 
 * They do not reproduce but only gain energy by consuming Algae. So the survival of their entire 
 * type depends on initial CritterK's surviving by eating Algae. 
 */

package assignment4;

import assignment4.Critter.TestCritter;

public class CritterK extends TestCritter {

	@Override
	public void doTimeStep() {
		walk(0);
	}

	@Override
	public boolean fight(String type) { 
		if(type == "@"){
			return true;
		}
		else{
			int num = Critter.getRandomInt(8);
			if(num % 2 == 1){
				return true;
			}
		run(getRandomInt(8));	
		return false;
		}
	}
	
	@Override
	public String toString() { return "K"; }
}
