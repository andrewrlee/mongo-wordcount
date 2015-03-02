package uk.co.optimisticpanda.wordcount.cli;

import java.io.File;
import java.util.UUID;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Main {

	private final Options options = new Options();
	
	private Main(String[] args){
		new JCommander(options, args);
	}
	
	private static class Options {
		@Parameter(names="-source", description="Source File")
		public File sourceFile = new File("dump.xml");

		@Parameter(names = { "-mongo"}, description = "Mongo Connection String [host:port]")
		public String mongo = "localhost:27017";

		@Parameter(names = "-id", description = "string to identify this process")
		public String id = UUID.randomUUID().toString();
	}

	public static void main(String[] args) {
		new Main(args);
	}
}
