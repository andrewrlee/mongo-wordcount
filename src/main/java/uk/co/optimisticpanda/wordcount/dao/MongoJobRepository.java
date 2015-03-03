package uk.co.optimisticpanda.wordcount.dao;

import java.io.File;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.optimisticpanda.wordcount.Main.Configuration;
import uk.co.optimisticpanda.wordcount.core.FileOperations;
import uk.co.optimisticpanda.wordcount.core.ProcessedChunk;
import uk.co.optimisticpanda.wordcount.core.UnprocessedChunk;

import com.google.common.base.Throwables;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MongoJobRepository implements JobRepository{
	private static Logger log = LoggerFactory.getLogger(MongoJobRepository.class);
	private MongoClient mongoClient;
	private DB db;
	private String nodeId;
	
	public enum Collection{
		WORK_QUEUE;
	}
	
	public MongoJobRepository(Configuration configuration, String dbName) {
		try {
			this.mongoClient = new MongoClient(configuration.getMongoHost(), configuration.getMongoPort());
			this.db = mongoClient.getDB(dbName);
			this.nodeId = configuration.id;
		} catch (UnknownHostException e) {
			throw Throwables.propagate(e);
		}
	}

	public void insert(Collection coll, BasicDBObject dbObject) {
		DBCollection collection = db.getCollection(coll.name());
		collection.insert(dbObject);
	}
	
	public DBCursor get(){
		DBCollection collection = db.getCollection("app");
		return collection.find();
	}
	
	public void close(){
		mongoClient.close();
	}

	@Override
	public boolean isLeader() {
		return true;
	}

	@Override
	public void addProcessedChunk(ProcessedChunk chunk) {
		
	}

	//TODO remove me
	AtomicInteger index = new AtomicInteger(1);
	@Override
	public Optional<UnprocessedChunk> getNextChunk() {
		FileOperations fileOperations = new FileOperations(new File("/home/alee/data/enwiki-20140707-pages-articles-multistream.xml"));
		UnprocessedChunk chunk = fileOperations.getChunk(index.getAndIncrement());
		return Optional.of(chunk);
	}

	@Override
	public void register() {
		log.info("Registered node with id {}", nodeId);
	}

	@Override
	public boolean unprocessedChunksRemain() {
		return false;
	}

	@Override
	public void addChunksToProcess(int numberOfChunks) {
		// TODO Auto-generated method stub
		
	}
}
