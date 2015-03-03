package uk.co.optimisticpanda.wordcount.core;

import static com.google.common.base.Charsets.UTF_8;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.base.Throwables;
import com.google.common.collect.Iterables;

public class FileOperations {

	private static final long _20_MEGABYTES = 20 * 1024 * 1024;
	private static final Logger log = LoggerFactory.getLogger(FileOperations.class);
	private File file;

	public FileOperations(File file) {
		this.file = file;
	}

	public UnprocessedChunk getChunk(int chunkIndex) {
		try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
			long seek = (chunkIndex - 1) * _20_MEGABYTES;
			int bufferSize = (int) Math.min(_20_MEGABYTES, randomAccessFile.length() - seek);
			byte[] byteBuffer = new byte[bufferSize];
			log.debug(String.format("start-pos: %s end-pos: %s, end: %s\n", seek, (seek + bufferSize), randomAccessFile.length()));
			randomAccessFile.seek(seek);
			randomAccessFile.read(byteBuffer);
			return new UnprocessedChunk(chunkIndex, new String(byteBuffer, UTF_8));
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	public List<String> getChunkAsLines(int chunkIndex) {
		String rawString = getChunk(chunkIndex).getText();
		return Splitter.on(System.getProperty("line.separator")).splitToList(rawString);
	}

	public int getNumberOfChunks() {
		try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
			int fullChunks = Long.valueOf(randomAccessFile.length() / _20_MEGABYTES).intValue();
			boolean evenlyDivisible = randomAccessFile.length() % _20_MEGABYTES == 0;
			return evenlyDivisible ? fullChunks : fullChunks + 1;
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	public static void main(String[] args) throws IOException {

		String filePath = "/home/alee/data/enwiki-20140707-pages-articles-multistream.xml";
		FileOperations fileOperations = new FileOperations(new File(filePath));

		int numberOfChunks = fileOperations.getNumberOfChunks();
		log.info("Number of chunks:" + numberOfChunks);
		log.info("Last chunk size:" + fileOperations.getChunkAsLines(numberOfChunks).size());
		log.info("Last line of last chunk:" + Iterables.getLast(fileOperations.getChunkAsLines(2336)));
	}

}
