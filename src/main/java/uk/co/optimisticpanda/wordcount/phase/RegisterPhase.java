package uk.co.optimisticpanda.wordcount.phase;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.optimisticpanda.wordcount.Util;
import uk.co.optimisticpanda.wordcount.dao.JobRepository;


public class RegisterPhase implements Phase {
	private static Logger log = LoggerFactory.getLogger(RegisterPhase.class);
	private JobRepository repo;

	public RegisterPhase(JobRepository repo){
		this.repo = repo;
	}
	
	@Override
	public boolean isComplete() {
		//FIXME Externalize
		Duration seconds = Duration.ofSeconds(1);
		log.info("Waiting for other nodes to register: {} seconds", seconds.getSeconds());
		Util.wait(seconds);
		return true;
	}

	@Override
	public void execute() {
		repo.register();
	}

}
