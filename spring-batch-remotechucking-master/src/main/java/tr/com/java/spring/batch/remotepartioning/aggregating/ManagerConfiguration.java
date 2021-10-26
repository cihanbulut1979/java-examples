package tr.com.java.spring.batch.remotepartioning.aggregating;


import java.util.Arrays;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.integration.chunk.RemoteChunkingManagerStepBuilderFactory;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;

import tr.com.java.spring.batch.remotepartioning.BrokerConfiguration;


@Configuration
@EnableBatchProcessing
@EnableBatchIntegration
@Import(value = {DataSourceConfiguration.class, BrokerConfiguration.class})
public class ManagerConfiguration {

	private final JobBuilderFactory jobBuilderFactory;

	private final RemoteChunkingManagerStepBuilderFactory managerStepBuilderFactory;
	
	private JobLauncher jobLauncher;

	public ManagerConfiguration(JobBuilderFactory jobBuilderFactory,
			RemoteChunkingManagerStepBuilderFactory managerStepBuilderFactory,
								JobLauncher jobLauncher) {

		this.jobBuilderFactory = jobBuilderFactory;
		this.managerStepBuilderFactory = managerStepBuilderFactory;
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
	public IntegrationFlow inboundFlow(ActiveMQConnectionFactory connectionFactory) {
		return IntegrationFlows
				.from(Jms.messageDrivenChannelAdapter(connectionFactory).destination("replies"))
				.channel(replies())
				.get();
	}

	/*
	 * Configure master step components
	 */
	@Bean
	public ListItemReader<Integer> itemReader() {
		return new ListItemReader<>(Arrays.asList(1, 2, 3, 4, 5, 6));
	}

	@Bean
	public TaskletStep managerStep() {
		return this.managerStepBuilderFactory.get("managerStep")
				.<Integer, Integer>chunk(3)
				.reader(itemReader())
				.outputChannel(requests())
				.inputChannel(replies())
				.build();
	}

	@Bean
	public Job remoteChunkingJob() {
		return this.jobBuilderFactory.get("remoteChunkingJob3")
				.start(managerStep())
				.build();
	}

	
	/*
	@Scheduled(fixedRate = 600000)
	public void start() throws Exception {
		System.out.println(" Job Started at :" + new Date());
		JobParameters param = new JobParametersBuilder().addString("JobID" + String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis())).toJobParameters();
		Job job = remotePartitioningJob();
		JobExecution execution = jobLauncher.run(job, param);
		System.out.println("Job finished with status :" + execution.getStatus());
	}
	
*/
	

	
}