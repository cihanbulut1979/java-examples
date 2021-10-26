package tr.com.java.spring.batch.remorepartioning2;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableBatchProcessing
public class ManagerSprinpBatchApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(ManagerSprinpBatchApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("jobLauncherTestUtils is launched");
	}
}
