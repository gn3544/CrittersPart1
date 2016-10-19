/* CRITTERS CommandParse.java
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
import java.lang.reflect.*;
import java.util.*;



public class CommandParse {
	
	private static String command = "";
	private static boolean flag = false;
	
	public static boolean processCommand(Scanner line){
		
		flag = false;	
		
		//parse the end user command; split into tokens
		command = line.nextLine();
		String delim = "[\t]+";
		String[] noTabs = command.split(delim); //get rid of all tabs
		String temp = " ";
		for(int i = 0; i < noTabs.length; i ++){  
			temp = temp + " " + noTabs[i];
		}
		String delims = "[ ]+";
		String[] noSpace = temp.split(delims); //get rid of all spaces
		if(noSpace.length == 0){
			System.out.println("invalid command: ");
			return flag;
		}
		String[] tokens = new String[noSpace.length-1];
		for(int i = 0; i < tokens.length; i ++){
			tokens[i] = noSpace[i + 1];
		} 
		
		//call appropriate execute function
		if(tokens[0].equals("quit")){
			if(tokens.length == 1){
				return true;
			}
			else{
				processError(tokens);
			}
		}	
		else if(tokens[0].equals("show")){
			if(tokens.length == 1){			 
				Critter.displayWorld();
			}
			else{
				processError(tokens);
			}
		}	
		else if(tokens[0].equals("step")){
			if(tokens.length > 2){
				processError(tokens);
			}  
			else if(tokens.length == 1){
				executeStep(1);
			}
			else if((tokens.length == 2)){
				try{
					int num = Integer.parseInt(tokens[1]);
					if(num < 0) processError(tokens);
					executeStep(num);
				}catch(NumberFormatException e){
					processError(tokens);
				}
			}
		}
		else if(tokens[0].equals("seed")){  
			if(tokens.length == 2){
				try{
					Long num = Long.parseLong(tokens[1]);
					if(num < 0) processError(tokens);
					executeSeed(num);					
				}catch(NumberFormatException e1){
					processError(tokens);
				}
			}
			else{
				processError(tokens);
			}
		}
		else if(tokens[0].equals("make")){
			if(tokens.length < 2 || tokens.length > 3){
				processError(tokens);
			}
			else if(tokens.length == 2){
				try{
				Critter.makeCritter(tokens[1]);	
				}
				catch(InvalidCritterException e2){
					processError(tokens);
				}
			}
			else if(tokens.length == 3){
				try{
					int numCall = Integer.parseInt(tokens[2]);
					if((numCall < 0)||(numCall != (int)numCall)){
						processError(tokens);
					}
					for (int i = 0; i < numCall; i ++){
						try{
							Critter.makeCritter(tokens[1]);
						}
						catch(InvalidCritterException e3){
							processError(tokens);
							break;
						}
					}
				}
				catch(NumberFormatException e4){
					processError(tokens);
				}
			}
		}
		else if(tokens[0].equals("stats")){
			if(tokens.length != 2){
				processError(tokens);
			}
			else{
				try{
				String myPackage = Critter.class.getPackage().toString().split(" ")[1];
				List<Critter> instances = Critter.getInstances(tokens[1]);		
				Class <?> cls = Class.forName(myPackage + "." + tokens[1]);
				Method method = cls.getMethod("runStats", List.class);
				method.invoke(cls, instances);
				}
				catch(InvalidCritterException e){
					processError(tokens);
				}
				catch(ClassNotFoundException e){
					processError(tokens);
				}
				catch(NoSuchMethodException e){
					processError(tokens);
				}
				catch(InvocationTargetException e){
					processError(tokens);
				}
				catch(IllegalArgumentException e){
					processError(tokens);
				}
				catch(IllegalAccessException e){
					processError(tokens);
				}
			}
		}
		else{//For invalid commands, print error message here
			String errorMsg;
			if(line.hasNextLine()){
				errorMsg = line.nextLine();				
			} 
			else{
				errorMsg = "";
			} 

			System.out.println("invalid command: " + command + errorMsg);
		}
		
		//return flag for while loop
		return flag;
	}  
	
	public static void executeStep(int numStep){
		for(int i = 0; i < numStep; i ++){
			Critter.worldTimeStep();
		}
	}
	public static void executeSeed(long number){
		Critter.setSeed(number);
	}
	
	public static void processError(String[] message){
		System.out.print("error processing:");
		for(String word : message){
			System.out.print(" " + word);
		}
		System.out.println();
	}
}
