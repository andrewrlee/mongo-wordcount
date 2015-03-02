package uk.co.optimisticpanda.wordcount.cli;

import java.io.File;
import java.util.UUID;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.base.Splitter;

public class Main {

	private final Options options = new Options();
	
	public Main(String[] args){
		new JCommander(options, args);
	}
	
	public static class Options {
		@Parameter(names="-source", description="Source File")
		public File sourceFile = new File("dump.xml");

		@Parameter(names = { "-mongo"}, description = "Mongo Connection String [host:port]")
		public String mongo = "localhost:27017";

		@Parameter(names = "-id", description = "string to identify this process")
		public String id = UUID.randomUUID().toString();
		
		public int getMongoPort(){
			return Integer.valueOf(Splitter.on(':').splitToList(mongo).get(1)).intValue();
		}
		
		public String getMongoHost(){
			return Splitter.on(':').splitToList(mongo).get(0);
		}
	}

	public Options getOptions() {
		return options;
	}
	
	public static void main(String[] args) {
		new Main(args);
	}
}
