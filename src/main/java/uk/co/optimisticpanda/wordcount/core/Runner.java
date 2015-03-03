package uk.co.optimisticpanda.wordcount.core;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.optimisticpanda.wordcount.Main.Configuration;
import uk.co.optimisticpanda.wordcount.Util;
import uk.co.optimisticpanda.wordcount.dao.JobRepository;
import uk.co.optimisticpanda.wordcount.dao.MongoJobRepository;
import uk.co.optimisticpanda.wordcount.phase.CombineResultsPhase;
import uk.co.optimisticpanda.wordcount.phase.DivvyWorkPhase;
import uk.co.optimisticpanda.wordcount.phase.Phase;
import uk.co.optimisticpanda.wordcount.phase.ProcessChunksPhase;
import uk.co.optimisticpanda.wordcount.phase.RegisterPhase;

public class Runner {

	private static Logger log = LoggerFactory.getLogger(Runner.class);
	private final JobRepository jobRepository;
	private final FileOperations fileOps;

	public Runner(Configuration configuration) {
		log.info("Running with configuration:" + configuration);
		this.fileOps = new FileOperations(configuration.sourceFile);
		this.jobRepository = new MongoJobRepository(configuration, "app");
	}

	public void run() {
		Phase[] phases = new Phase[] { //
		        new RegisterPhase(jobRepository), // 
				new DivvyWorkPhase(jobRepository, fileOps), //
				new ProcessChunksPhase(jobRepository), //
				new CombineResultsPhase() };

		for (Phase phase : phases) {
			log.info("Running phase: {}", phase.getClass().getSimpleName());
			phase.execute();
			while (!phase.isComplete()) {
				Util.wait(Duration.ofMillis(500));
			}
		}
	}

	
}
