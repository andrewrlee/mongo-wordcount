package uk.co.optimisticpanda.wordcount;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Throwables;

public class Util {

	public static void wait(Duration duration) {
		try {
			TimeUnit.MILLISECONDS.sleep(duration.toMillis());
		} catch (InterruptedException e) {
			throw Throwables.propagate(e);
		}
	}
	
}
