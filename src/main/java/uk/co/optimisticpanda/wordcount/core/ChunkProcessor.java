package uk.co.optimisticpanda.wordcount.core;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.internal.Maps;
import com.google.common.base.Splitter;

public class ChunkProcessor {

	private static Logger log = LoggerFactory.getLogger(ChunkProcessor.class);

	public ProcessedChunk process(UnprocessedChunk chunk) {
		Map<String, Integer> histogram = Maps.newHashMap();

		Iterable<String> words = Splitter.onPattern("[^A-Za-z]+")//
				.omitEmptyStrings().trimResults().split(chunk.getText());

		for (String word : words) {
			Integer count = histogram.get(word);
			if (count == null) {
				histogram.put(word, Integer.valueOf(0));
			} else {
				histogram.put(word, Integer.valueOf(count.intValue() + 1));
			}
		}
		log.info("finished processing chunk {}, found {} words", chunk.getIndex(), histogram.size());
		return new ProcessedChunk(chunk.getIndex(), histogram);
	}
}
