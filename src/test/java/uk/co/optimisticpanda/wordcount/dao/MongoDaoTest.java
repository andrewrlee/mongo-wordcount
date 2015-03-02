package uk.co.optimisticpanda.wordcount.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoDaoTest {

	private MongoDao mongoDao;

	@Before
    public void setup(){
		mongoDao = new MongoDao("localhost", 27017, "test");
	}
	
	@After
	public void teardown(){
		mongoDao.close();
	}
	
	@Test
	public void doStuff(){
		mongoDao.insert();
		DBCursor dbCursor = mongoDao.get();
		for (DBObject dbObject : dbCursor) {
			System.out.println(dbObject);
		}
	}
	
}
