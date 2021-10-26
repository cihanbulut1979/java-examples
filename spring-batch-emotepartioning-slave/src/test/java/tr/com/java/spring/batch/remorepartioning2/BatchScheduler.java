package tr.com.java.spring.batch.remorepartioning2;

import javax.sql.DataSource;

import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import tr.com.java.spring.batch.remotepartioning.aggregating.DataSourceConfiguration;
import tr.com.java.spring.batch.remotepartioning.aggregating.WorkerConfiguration;

@EnableScheduling
@Configuration
@Import(value = { DataSourceConfiguration.class, WorkerConfiguration.class })
public class BatchScheduler {
	
	

	@Bean
	public JobRepositoryFactoryBean jobRepositoryFactory(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDataSource(dataSource);
		factory.setTransactionManager(transactionManager);
		
		factory.afterPropertiesSet();
		return factory;
	}

	@Bean
	public JobRepository jobRepository(JobRepositoryFactoryBean factory) throws Exception {
		return (JobRepository) factory.getObject();
	}

	@Bean
	public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
		SimpleJobLauncher launcher = new SimpleJobLauncher();
		launcher.setJobRepository(jobRepository);
		return launcher;
	}

}
