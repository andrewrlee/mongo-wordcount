package uk.co.optimisticpanda.wordcount.phase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.optimisticpanda.wordcount.core.FileOperations;
import uk.co.optimisticpanda.wordcount.dao.JobRepository;

public class DivvyWorkPhase implements Phase{
	private static Logger log = LoggerFactory.getLogger(DivvyWorkPhase.class);
	private JobRepository repo;
	private FileOperations fileOperations;

	public DivvyWorkPhase(JobRepository repo, FileOperations fileOperations) {
		this.repo = repo;
		this.fileOperations = fileOperations;
	}

	@Override
	public boolean isComplete() {
		return true;
	}

	@Override
	public void execute() {
		if(repo.isLeader()){
			int numberOfChunks = fileOperations.getNumberOfChunks();
			log.debug("{} chunks to process", numberOfChunks);
			for (int i = 1; i < numberOfChunks; i++) {
				repo.addChunksToProcess(numberOfChunks);
				if(i % 250 == 0){
					log.debug("Proccessed chunk {} out of {}", i, numberOfChunks);
				}
			}
		}
	}

}
