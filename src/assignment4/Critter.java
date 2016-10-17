/* CRITTERS Critter.java
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
package assignment4;

import java.util.*; //fine to change import statements?


/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	protected final void walk(int direction) {
	}
	
	protected final void run(int direction) {
		
	}
	
	protected final void reproduce(Critter offspring, int direction) {
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try{
			Class<?> critterClass = Class.forName(myPackage + "." + critter_class_name);
			Critter newCritter = (Critter) critterClass.newInstance();
			newCritter.x_coord = getRandomInt(Params.world_width);
			newCritter.y_coord = getRandomInt(Params.world_height);
			newCritter.energy = Params.start_energy;
			population.add(newCritter);
		}
		catch (ClassNotFoundException e1){
			throw new InvalidCritterException(critter_class_name);
		}
		catch (InstantiationException e2){
			throw new InvalidCritterException(critter_class_name);
		}
		catch (IllegalAccessException e3){
			throw new InvalidCritterException(critter_class_name);
		}
		finally{
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try{
			Class<?> critterClass = Class.forName(myPackage + "." + critter_class_name);
			for (Critter critter: population){
				if (critter.getClass().equals(critterClass)){ //maybe use isAssignable
					result.add(critter);
				}
			}
		}
		catch (ClassNotFoundException e1){
			throw new InvalidCritterException(critter_class_name);
		}
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
	}
	
	public static void worldTimeStep() {
		
		HashMap<ArrayList<Integer>, ArrayList<Critter>> coordMap = new HashMap<ArrayList<Integer>, ArrayList<Critter>>();
		//coordMap is a mapping from coordinates to an arrayList of Critters to keep track of multiple Critters on a single coordinate
		for (Critter critter: population){ //first do time step, precondition is that all are alive
			critter.doTimeStep();
		}
		for (int i = 0; i < population.size(); i++){ //remove dead critters from doTimeStep()
			if (population.get(i).getEnergy() <= 0){
				population.remove(i);
			}
		}
		for (Critter critter: population){
			ArrayList<Integer> coordinates = new ArrayList<Integer>(2);
			coordinates.add(0, critter.x_coord);
			coordinates.add(1, critter.y_coord);
			if (!coordMap.containsKey(coordinates)){
				coordMap.put(coordinates, new ArrayList<Critter>());
			}
			coordMap.get(coordinates).add(critter);
		}
		encounter(coordMap);
		for (Critter babyCritter: babies){
			population.add(babyCritter);
		}
		for (Critter critter: population){
			critter.energy -= Params.rest_energy_cost;
		}
		for (int i = 0; i < population.size(); i++){ //remove dead critters from encounter()
			if (population.get(i).getEnergy() <= 0){
				population.remove(i);
			}
		}
		babies.clear();
	}
	
	private static void encounter(HashMap<ArrayList<Integer>, ArrayList<Critter>> location){
		
	}
	public static void displayWorld() {
		//construct critterWorld
		String[][] critterWorld = new String[Params.world_height + 2][Params.world_width + 2];
		
		//unoccupited spaces are initially empty
		for(int i = 0; i < Params.world_height + 2; i ++){
			for(int j = 0; j < Params.world_width + 2; j ++){
				critterWorld[i][j] = " ";
			}
		}
		
		//construct borders
		critterWorld[0][0] = "+";
		critterWorld[0][Params.world_width + 1] = "+";
		critterWorld[Params.world_height + 1][0] = "+";
		critterWorld[Params.world_height + 1][Params.world_width + 1] = "+";
		for(int i = 1; i <= Params.world_width; i ++){
			critterWorld[0][i] = "-";
			critterWorld[Params.world_height + 1][i] = "-";
		}
		for(int i = 1; i <= Params.world_height; i ++){
			critterWorld[i][0] = "|";
			critterWorld[i][Params.world_width + 1] = "|";
		}
		
		// iterate though critter collection
		// get position, and place critters on critterWorld
		for(Critter s : population){
			critterWorld[s.x_coord + 1][s.y_coord + 1] = s.toString(); 
		}
		
		// print out critterWorld to System.out
		for(int i = 0; i < Params.world_height + 2; i ++){
			for(int j = 0; j < Params.world_width + 2; j ++){
				System.out.print(critterWorld[i][j]);
			}
			System.out.println();
		}
	}//end displayWorld()
}
