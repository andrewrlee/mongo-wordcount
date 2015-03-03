package uk.co.optimisticpanda.wordcount;

import java.io.File;
import java.util.UUID;

import uk.co.optimisticpanda.wordcount.core.Runner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;

public class Main {

	@VisibleForTesting
	final Configuration configuration = new Configuration();

	public Main(String[] args) {
		new JCommander(configuration, args);
		new Runner(configuration).run();
	}

	public static class Configuration {
		@Parameter(names = "-source", description = "Source File")
		public File sourceFile = new File("dump.xml");

		@Parameter(names = { "-mongo" }, description = "Mongo Connection String [host:port]")
		public String mongo = "localhost:27017";

		@Parameter(names = "-id", description = "string to identify this process")
		public String id = UUID.randomUUID().toString();

		public int getMongoPort() {
			return Integer.valueOf(Splitter.on(':').splitToList(mongo).get(1)).intValue();
		}

		public String getMongoHost() {
			return Splitter.on(':').splitToList(mongo).get(0);
		}

		@Override
		public String toString() {
			return "Configuration [sourceFile=" + sourceFile + ", id=" + id + ", getMongoPort()=" + getMongoPort() + ", getMongoHost()=" + getMongoHost() + "]";
		}
		
	}

	public static void main(String[] args) {
		new Main(args);
	}
}
