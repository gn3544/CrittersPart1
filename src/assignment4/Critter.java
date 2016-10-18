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
	private boolean alreadyMoved = false;
	private boolean isFighting = false;
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
		int offset = 1; //changes direction of this by walking 1 unit
		int x_offset = 0, y_offset = 0;
		this.energy -= Params.walk_energy_cost;
		HashMap<ArrayList<Integer>, ArrayList<Critter>> coordMap = getCoordMap();
		
		if ((direction == 0) || (direction == 1) || (direction == 7)){
			x_offset = offset;
		}
		else if ((direction == 3) || (direction == 4) || (direction == 5)){
			x_offset = -1*offset;
		}
		
		if ((direction == 1) || (direction == 2) || (direction == 3)){
			y_offset = -1*offset;
		}
		else if ((direction == 5) || (direction == 6) || (direction == 7)){
			y_offset = offset;
		}
		
		if (!alreadyMoved){
			ArrayList<Integer> newCoord = new ArrayList<Integer>(2);
			newCoord.add(0, x_coord + x_offset);
			newCoord.add(1, y_coord + y_offset);
			if ((isFighting && !coordMap.containsKey(newCoord)) || !isFighting){
				x_coord += x_offset;
				y_coord += y_offset;
				alreadyMoved = true;
			}
		}
	}
	
	protected final void run(int direction) {
		int offset = 2; //changes direction of this by running 2 units
		int x_offset = 0, y_offset = 0;
		this.energy -= Params.run_energy_cost;
		HashMap<ArrayList<Integer>, ArrayList<Critter>> coordMap = getCoordMap();
		
		if ((direction == 0) || (direction == 1) || (direction == 7)){
			x_offset = offset;
		}
		else if ((direction == 3) || (direction == 4) || (direction == 5)){
			x_offset = -1*offset;
		}
		
		if ((direction == 1) || (direction == 2) || (direction == 3)){
			y_offset = -1*offset;
		}
		else if ((direction == 5) || (direction == 6) || (direction == 7)){
			y_offset = offset;
		}
		
		if (!alreadyMoved){
			ArrayList<Integer> newCoord = new ArrayList<Integer>(2);
			newCoord.add(0, x_coord + x_offset);
			newCoord.add(1, y_coord + y_offset);
			if ((isFighting && !coordMap.containsKey(newCoord)) || !isFighting){
				x_coord += x_offset;
				y_coord += y_offset;
				alreadyMoved = true;
			}
		}
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		int x_offset = 0, y_offset = 0; //changes direction of offspring
		if ((direction == 0) || (direction == 1) || (direction == 7)){
			x_offset = 1;
		}
		else if ((direction == 3) || (direction == 4) || (direction == 5)){
			x_offset = -1;
		}
		
		if ((direction == 1) || (direction == 2) || (direction == 3)){
			y_offset = -1;
		}
		else if ((direction == 5) || (direction == 6) || (direction == 7)){
			y_offset = 1;
		}
		try{
			if (this.energy > 0 && this.energy >= Params.min_reproduce_energy){
				offspring = (Critter) this.getClass().newInstance();
				offspring.energy = this.energy/2; //rounding down energy
				this.energy -= offspring.energy; //rounding up energy
				offspring.x_coord = this.x_coord + x_offset;
				offspring.y_coord = this.y_coord + y_offset;
				babies.add(offspring);
			}
		}
		catch (Exception e1){ //must catch exceptions thrown by newInstance() 
			
		}
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
			newCritter.x_coord = getRandomInt(Params.world_width + 1);
			newCritter.y_coord = getRandomInt(Params.world_height + 1);
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
				if (critter.getClass().equals(critterClass)){
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
		
		for (Critter critter: population){
			critter.alreadyMoved = false;
		}
		
		for (Critter critter: population){ //first do time step, precondition is that all are alive
			critter.doTimeStep();
		}
		
		for (int i = 0; i < population.size(); i++){ //remove dead critters from doTimeStep()
			if (population.get(i).getEnergy() <= 0){
				population.remove(i);
				i--;
			}
		}
		
		encounter(getCoordMap()); //fix all encounters
		
		for (Critter critter: population){ //update rest energy
			critter.energy -= Params.rest_energy_cost;
		}
		
		for (int i = 0; i < population.size(); i++){ //remove dead critters after encounter and staying still
			if (population.get(i).getEnergy() <= 0){
				population.remove(i);
				i--;
			}
		}
		generateAlgae();
		population.addAll(babies);
		babies.clear();
	}
	
	private static void encounter(HashMap<ArrayList<Integer>, ArrayList<Critter>> location){
		for (ArrayList<Integer> key: location.keySet()){
			for (int i = 0; i < location.get(key).size(); i++){
				for (int j = i + 1; j < location.get(key).size(); j++){
					critterEncounter(location.get(key).get(i), location.get(key).get(j));
				}
			}
		}
	}
	
	private static void critterEncounter(Critter A, Critter B){
		
		boolean fightA = false, fightB = false;
		int rollA = 0, rollB = 0;
		if (A.energy > 0 && B.energy > 0){
			B.isFighting = true;
			A.isFighting = true;
			fightA = A.fight(B.toString()); //invoke the fight, add flag for walking and running
			fightB = B.fight(A.toString());
			B.isFighting = false;
			A.isFighting = false;
		}
		
		if (A.energy > 0 && B.energy > 0 && A.x_coord == B.x_coord && A.y_coord == B.y_coord){
			if (fightA){ //roll the dies
				rollA = Critter.getRandomInt(A.energy + 1);
			}
			
			if (fightB){
				rollB = Critter.getRandomInt(B.energy + 1);
			}
			
			if (rollA >= rollB){ //by default A is winner if rollA == rollB
				A.energy += B.energy/2; //NOT SURE if we round up or down on divide by 2
				B.energy = 0;
			}
			else{
				B.energy += A.energy/2;
				A.energy = 0;
			}
		}
		
	}
	
	private static void generateAlgae(){
		try{
			for (int i = 0; i < Params.refresh_algae_count; i++){
				makeCritter("Algae");
			}
		}
		catch (Exception e1){
			
		}
	}
	
	private static HashMap<ArrayList<Integer>, ArrayList<Critter>> getCoordMap(){
		HashMap<ArrayList<Integer>, ArrayList<Critter>> coordMap = new HashMap<ArrayList<Integer>, ArrayList<Critter>>();
		//coordMap is a mapping from coordinates to an arrayList of Critters to keep track of multiple Critters on a single coordinate
		for (Critter critter: population){
			ArrayList<Integer> coordinates = new ArrayList<Integer>(2);
			coordinates.add(0, critter.x_coord);
			coordinates.add(1, critter.y_coord);
			if (!coordMap.containsKey(coordinates)){
				coordMap.put(coordinates, new ArrayList<Critter>());
			}
			coordMap.get(coordinates).add(critter);
		}
		return coordMap;
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
