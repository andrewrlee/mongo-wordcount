package uk.co.optimisticpanda.wordcount.phase;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.optimisticpanda.wordcount.core.ChunkProcessor;
import uk.co.optimisticpanda.wordcount.core.ProcessedChunk;
import uk.co.optimisticpanda.wordcount.core.UnprocessedChunk;
import uk.co.optimisticpanda.wordcount.dao.JobRepository;

import com.beust.jcommander.internal.Maps;
import com.google.common.base.Splitter;

public class ProcessChunksPhase implements Phase {
	private static Logger log = LoggerFactory.getLogger(ProcessChunksPhase.class);
	private JobRepository repo;
	private ChunkProcessor chunkProcessor = new ChunkProcessor();

	public ProcessChunksPhase(JobRepository repo) {
		this.repo = repo;
	}

	@Override
	public boolean isComplete() {
		return !repo.unprocessedChunksRemain();
	}

	@Override
	public void execute() {
		Optional<UnprocessedChunk> chunk = Optional.empty();
		while((chunk = repo.getNextChunk()).isPresent()){
			ProcessedChunk processedChunk = chunkProcessor.process(chunk.get());
			repo.addProcessedChunk(processedChunk);
		}
	}

}
