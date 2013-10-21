package de.simonchristmann.sixlowpanviewer;



public class MainClass {
	public static void main(String[] args) {
		
		// Print Arguments for debugging
		if(Settings.debug_output) {
			for(int i = 0; i < args.length; i++) {
				System.out.println(i+": "+args[i]);
			}
		}
		
		// Get arguments into own class
		Arguments arg = new Arguments(args);
		
		// Give control to controller class
        BcViewController bcviewcontroller = new BcViewController(arg);
	}
}
