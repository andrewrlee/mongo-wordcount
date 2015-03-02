package uk.co.optimisticpanda.wordcount.dao;

import java.net.UnknownHostException;

import com.google.common.base.Throwables;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MongoDao {

	private MongoClient mongoClient;
	private DB db;

	public MongoDao(String hostname, int port, String dbName) {
		try {
			mongoClient = new MongoClient(hostname, port);
			db = mongoClient.getDB(dbName);
		} catch (UnknownHostException e) {
			throw Throwables.propagate(e);
		}
	}

	public void insert() {
		DBCollection collection = db.getCollection("app");
		BasicDBObject doc = new BasicDBObject("name", "MongoDB")
        .append("type", "database")
        .append("count", 1)
        .append("info", new BasicDBObject("x", 203).append("y", 102));
		collection.insert(doc);
	}
	
	public DBCursor get(){
		DBCollection collection = db.getCollection("app");
		return collection.find();
	}
	
	public void close(){
		mongoClient.close();
	}
}
