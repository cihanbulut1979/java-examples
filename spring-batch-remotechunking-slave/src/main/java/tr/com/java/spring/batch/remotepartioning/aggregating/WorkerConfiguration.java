package tr.com.java.spring.batch.remotepartioning.aggregating;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.integration.chunk.RemoteChunkingWorkerBuilder;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;

import tr.com.java.spring.batch.remotepartioning.BrokerConfiguration;


@Configuration
@EnableBatchProcessing
@EnableBatchIntegration
@Import(value = { DataSourceConfiguration.class, BrokerConfiguration.class })
public class WorkerConfiguration {


	private final StepBuilderFactory stepBuilderFactory;

	private final ApplicationContext applicationContext;

	private final JobExplorer jobExplorer;
	
	@Autowired
	private RemoteChunkingWorkerBuilder<Integer, Integer> remoteChunkingWorkerBuilder;


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
	public ItemProcessor<Integer, Integer> itemProcessor() {
		return item -> {
 			System.out.println(Thread.currentThread().getName() + " processing item " + item);
			return item;
		};
	}

	@Bean
	public ItemWriter<Integer> itemWriter() {
		return items -> {
			for (Integer item : items) {
				System.out.println(Thread.currentThread().getName() + " writing item " + item);
			}
		};
	}

	@Bean
	public IntegrationFlow workerIntegrationFlow() {
		return this.remoteChunkingWorkerBuilder
				.itemProcessor(itemProcessor())
				.itemWriter(itemWriter())
				.inputChannel(requests())
				.outputChannel(replies())
				.build();
	}
	

}