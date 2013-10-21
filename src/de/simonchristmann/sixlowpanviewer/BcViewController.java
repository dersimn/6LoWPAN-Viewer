package de.simonchristmann.sixlowpanviewer;

import java.util.Scanner;

public class BcViewController {
	private BcReceiver bcReceive = new BcReceiver();
	
	public BcViewController(Arguments args) {
		bcReceive.setPort(args.port);
		bcReceive.setInterface(args.iface);
		bcReceive.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
		       System.out.println("Closing application..");
		       bcReceive.close();
		       System.out.println("Done.");
		    }
		 });
		
		//testInput();
	}
	
	public static void testInput() {
		System.out.print("Enter Text: ");
		Scanner scan = new Scanner(System.in);

		String text = scan.nextLine();

		System.out.println("You gave me this: "+text);
	}
}
