package tr.com.java.spring.batch.remorepartioning2;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SlaveSprinpBatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(SlaveSprinpBatchApplication.class, args);
	}
}
