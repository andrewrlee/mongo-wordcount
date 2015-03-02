package uk.co.optimisticpanda.wordcount.cli;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.UUID;

import org.junit.Test;

import uk.co.optimisticpanda.wordcount.cli.Main.Options;
public class MainTest {

	@Test
	public void checkDefaults(){
		Options options = new Main(new String[0]).getOptions();
		UUID.fromString(options.id);
		assertThat(options.sourceFile).isEqualTo(new File("dump.xml"));
		assertThat(options.mongo).isEqualTo("localhost:27017");
		assertThat(options.getMongoHost()).isEqualTo("localhost");
		assertThat(options.getMongoPort()).isEqualTo(27017);
	}

	@Test
	public void checkOverride(){
		Options options = new Main(new String[]{"-id", "myid", "-mongo", "127.0.0.1:1234", "-source", "file.xml"}).getOptions();
		assertThat(options.id).isEqualTo("myid");
		assertThat(options.sourceFile).isEqualTo(new File("file.xml"));
		assertThat(options.mongo).isEqualTo("127.0.0.1:1234");
		assertThat(options.getMongoHost()).isEqualTo("127.0.0.1");
		assertThat(options.getMongoPort()).isEqualTo(1234);
	}

}

