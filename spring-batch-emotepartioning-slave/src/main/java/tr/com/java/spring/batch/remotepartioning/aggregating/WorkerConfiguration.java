package tr.com.java.spring.batch.remotepartioning.aggregating;

import java.util.Map;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.integration.partition.BeanFactoryStepLocator;
import org.springframework.batch.integration.partition.StepExecutionRequestHandler;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import tr.com.java.spring.batch.remotepartioning.BrokerConfiguration;

/**
 * This configuration class is for the worker side of the remote partitioning
 * sample. Each worker will process a partition sent by the manager step.
 *
 */
@Configuration
@EnableBatchProcessing
@EnableBatchIntegration
@Import(value = { DataSourceConfiguration.class, BrokerConfiguration.class })
public class WorkerConfiguration {


	private final StepBuilderFactory stepBuilderFactory;

	private final ApplicationContext applicationContext;

	private final JobExplorer jobExplorer;


	public WorkerConfiguration(StepBuilderFactory stepBuilderFactory,
								JobExplorer jobExplorer,
								ApplicationContext applicationContext) {

		this.stepBuilderFactory = stepBuilderFactory;
		this.applicationContext = applicationContext;
		this.jobExplorer = jobExplorer;
	}

	/*
	 * Configure inbound flow (requests coming from the master)
	 */
	@Bean
	public DirectChannel requests() {
		return new DirectChannel();
	}

	@Bean
	public IntegrationFlow inboundFlow(ActiveMQConnectionFactory connectionFactory) {
		return IntegrationFlows
				.from(Jms.messageDrivenChannelAdapter(connectionFactory).destination("requests"))
				.channel(requests())
				.get();
	}

	/*
	 * Configure outbound flow (replies going to the master)
	 */
	@Bean
	public DirectChannel replies() {
		return new DirectChannel();
	}

	@Bean
	public IntegrationFlow outboundFlow(ActiveMQConnectionFactory connectionFactory) {
		return IntegrationFlows
				.from(replies())
				.handle(Jms.outboundAdapter(connectionFactory).destination("replies"))
				.get();
	}

	/*
	 * Configure worker components
	 */
	@Bean
	@ServiceActivator(inputChannel = "requests", outputChannel = "replies")
	public StepExecutionRequestHandler stepExecutionRequestHandler() {
		StepExecutionRequestHandler stepExecutionRequestHandler = new StepExecutionRequestHandler();
		stepExecutionRequestHandler.setJobExplorer(this.jobExplorer);
		BeanFactoryStepLocator stepLocator = new BeanFactoryStepLocator();
		stepLocator.setBeanFactory(this.applicationContext);
		stepExecutionRequestHandler.setStepLocator(stepLocator);
		return stepExecutionRequestHandler;
	}

	@Bean
	public Step slaveStep() {
		return this.stepBuilderFactory.get("slaveStep")
				.tasklet(getTasklet(null))
				.taskExecutor(taskExecutor1())
				.build();
	}
		
	public TaskExecutor taskExecutor1() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setMaxPoolSize(5);
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setQueueCapacity(5);
		taskExecutor.afterPropertiesSet();
		return taskExecutor;
	}
	
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		
		taskExecutor.setConcurrencyLimit(5);
		taskExecutor.setThreadNamePrefix("Slave-");
		
		return taskExecutor;
	}

	@Bean
	@StepScope
	public Tasklet getTasklet(@Value("#{stepExecutionContext['partitionId']}") String partitionId) {
		return (contribution, chunkContext) -> {
			
			Map<String, Object> jobExecutionContext = chunkContext.getStepContext().getJobExecutionContext();
			
			Map<String, Object> stepExecutionContext = chunkContext.getStepContext().getStepExecutionContext();
			
			System.out.println(jobExecutionContext);
			System.out.println(stepExecutionContext);
						
			String id = String.valueOf(stepExecutionContext.get("id"));
			String value = String.valueOf(stepExecutionContext.get("value"));
			//String partitionId =String.valueOf(stepExecutionContext.get("partitionId"));
			
			System.out.println(Thread.currentThread().getName() + " --> processing partitionId = " + partitionId + " id = " + id + " value = " + value);
						
			return RepeatStatus.FINISHED;
		};
	}


}