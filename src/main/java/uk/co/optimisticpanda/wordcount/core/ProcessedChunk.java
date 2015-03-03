package uk.co.optimisticpanda.wordcount.core;

import java.util.Map;

public class ProcessedChunk {

	private Map<String, Integer> wordFreq;
	private int chunkIndex;

	public ProcessedChunk(int chunkIndex, Map<String, Integer> wordFreq) {
		this.chunkIndex = chunkIndex;
		this.wordFreq = wordFreq;
	}

}
