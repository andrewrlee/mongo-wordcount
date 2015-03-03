package uk.co.optimisticpanda.wordcount.dao;

import java.util.Optional;

import uk.co.optimisticpanda.wordcount.core.ProcessedChunk;
import uk.co.optimisticpanda.wordcount.core.UnprocessedChunk;

public interface JobRepository {

	public void register();
	public boolean isLeader();
	public void addChunksToProcess(int numberOfChunks);
	public void addProcessedChunk(ProcessedChunk chunk);
	public Optional<UnprocessedChunk> getNextChunk();
	public boolean unprocessedChunksRemain();
}
