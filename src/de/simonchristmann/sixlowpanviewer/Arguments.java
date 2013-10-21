package de.simonchristmann.sixlowpanviewer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Arguments {
	public int port;
	public String iface;

	@SuppressWarnings("static-access")
	public Arguments(String[] args) {
		// Argument Parser
		CommandLine lvCmd = null;
        CommandLineParser lvParser = new GnuParser();
        Options lvOptions = new Options();

        lvOptions.addOption("h", "help", false, "Show this help information.");
        lvOptions.addOption("v", "version", false, "Show version information");
		
		// Option -i en0
		lvOptions.addOption(OptionBuilder
			.withArgName( "interface" )
			.hasArg()
            .withDescription("Use given network interface for connection")
			.withLongOpt("interface")
            .create( "i" ));
		// Option -p 4321
		lvOptions.addOption(OptionBuilder
			.withArgName( "port" )
			.hasArg()
            .withDescription("Specify port on which we want to listen for broadcasts")
			.withLongOpt("port")
            .create( "p" ));	
		
		// Parse Arguments
		try {
            lvCmd = lvParser.parse(lvOptions, args);

            if (lvCmd.hasOption('h')) {
            	printHelp(lvOptions);
                return;
            }
            if (lvCmd.hasOption('v')) {
                System.out.println(Settings.versionNumber);
                return;
            }
            
            // Load options into variables
            if (lvCmd.hasOption("i")) {
            	this.iface = lvCmd.getOptionValue("i");
            } else {
            	this.iface = "en0";
            }
            if (lvCmd.hasOption("p")) {
            	// TODO What if argument isn't integer
            	this.port = Integer.parseInt(lvCmd.getOptionValue("p"));
            } else {
            	this.port = 4321;
            }
        } catch (ParseException pvException) {
        	printHelp(lvOptions);
            System.out.println("Invalid parameter:" + pvException.getMessage());
            return;
        }
	}
	
	public static void printHelp(Options lvOptions) {
        HelpFormatter lvFormater = new HelpFormatter();
        
		System.out.println(Settings.applicationName+" "+Settings.versionNumber+" by "+Settings.author+" ("+Settings.website+")");
		lvFormater.printHelp(Settings.cmdUsage, lvOptions);
	}
}
