package tr.com.java.spring.batch.remotepartioning.aggregating;


import java.util.Date;

import javax.sql.DataSource;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.integration.partition.MessageChannelPartitionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.AggregatorFactoryBean;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import tr.com.java.spring.batch.remotepartioning.BasicPartitioner;
import tr.com.java.spring.batch.remotepartioning.BrokerConfiguration;


@Configuration
@EnableBatchProcessing
@EnableBatchIntegration
@Import(value = {DataSourceConfiguration.class, BrokerConfiguration.class})
public class ManagerConfiguration {

	private static final int GRID_SIZE = 3;

	private static final long RECEIVE_TIMEOUT = 600000L;

	private final JobBuilderFactory jobBuilderFactory;

	private final StepBuilderFactory stepBuilderFactory;
	
	private JobLauncher jobLauncher;
	
	@Autowired
	private DataSource dataSource;


	public ManagerConfiguration(JobBuilderFactory jobBuilderFactory,
								StepBuilderFactory stepBuilderFactory,
								JobLauncher jobLauncher) {

		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.jobLauncher = jobLauncher;
	}
	
	

	/*
	 * Configure outbound flow (requests going to workers)
	 */
	@Bean
	public DirectChannel requests() {
		return new DirectChannel();
	}

	@Bean
	public IntegrationFlow outboundFlow(ActiveMQConnectionFactory connectionFactory) {
		return IntegrationFlows
				.from(requests())
				.handle(Jms.outboundAdapter(connectionFactory).destination("requests"))
				.get();
	}

	/*
	 * Configure inbound flow (replies coming from workers)
	 */
	@Bean
	public QueueChannel replies() {
		return new QueueChannel();
	}

	@Bean
	public DirectChannel inboundStaging() {
		return new DirectChannel();
	}

	@Bean
	public IntegrationFlow inboundStagingFlow(ActiveMQConnectionFactory connectionFactory) {
		return IntegrationFlows
				.from(Jms.messageDrivenChannelAdapter(connectionFactory).destination("replies"))
				.channel(inboundStaging())
				.get();
	}

	/*
	 * Configure master step components
	 */
	@Bean
	public Step masterStep() {
		Step masterStep = this.stepBuilderFactory.get("masterStep")
		.partitioner("slaveStep", partitioner())
		.partitionHandler(partitionHandler())
		.gridSize(GRID_SIZE).taskExecutor(taskExecutor())
		.build();
				
		return masterStep;
	}
	
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setCorePoolSize(10);
		taskExecutor.setQueueCapacity(10);
		taskExecutor.afterPropertiesSet();
		return taskExecutor;
	}

	@Bean
	public PartitionHandler partitionHandler() {
		MessageChannelPartitionHandler partitionHandler = new MessageChannelPartitionHandler();
		partitionHandler.setStepName("slaveStep");
		partitionHandler.setGridSize(GRID_SIZE);
		partitionHandler.setReplyChannel(replies());

		MessagingTemplate template = new MessagingTemplate();
		template.setDefaultChannel(requests());
		template.setReceiveTimeout(RECEIVE_TIMEOUT);
		partitionHandler.setMessagingOperations(template);

		return partitionHandler;
	}

	@Bean
	@ServiceActivator(inputChannel = "inboundStaging")
	public AggregatorFactoryBean partitioningMessageHandler() {
		AggregatorFactoryBean aggregatorFactoryBean = new AggregatorFactoryBean();
		aggregatorFactoryBean.setProcessorBean(partitionHandler());
		aggregatorFactoryBean.setOutputChannel(replies());
		return aggregatorFactoryBean;
	}

	public Job remotePartitioningJob() {		
		return this.jobBuilderFactory.get("remotePartitioningJob")
				.start(masterStep())
				.build();
	}
	
	@Scheduled(fixedRate = 600000)
	public void start() throws Exception {
		System.out.println(" Job Started at :" + new Date());
		JobParameters param = new JobParametersBuilder().addString("JobID" + String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis())).toJobParameters();
		Job job = remotePartitioningJob();
		JobExecution execution = jobLauncher.run(job, param);
		System.out.println("Job finished with status :" + execution.getStatus());
	}
	

	@Bean
	public Partitioner partitioner() {
		return new BasicPartitioner();
	}

	
}