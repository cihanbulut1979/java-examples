package tr.com.java.spring.batch.remotepartioning.aggregating;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

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
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;

import tr.com.java.spring.batch.domain.Customer;
import tr.com.java.spring.batch.domain.CustomerRowMapper;
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
public class WorkerConfiguration2 {


	private final StepBuilderFactory stepBuilderFactory;

	private final ApplicationContext applicationContext;

	private final JobExplorer jobExplorer;
	 
	@Autowired
	private DataSource dataSource;


	public WorkerConfiguration2(StepBuilderFactory stepBuilderFactory,
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
				. <Customer, Customer>chunk(1000)
                .reader(pagingItemReader(null, null))
				.build();
	}

	@Bean
	@StepScope
	public Tasklet getTasklet(@Value("#{stepExecutionContext['partition']}") String partition) {
		return (contribution, chunkContext) -> {
			System.out.println("processing " + partition);
			return RepeatStatus.FINISHED;
		};
	}
	
	@Bean
    @StepScope
    public JdbcPagingItemReader<Customer> pagingItemReader(
            @Value("#{stepExecutionContext['minValue']}") Long minValue,
            @Value("#{stepExecutionContext['maxValue']}") Long maxValue) 
    {
        System.out.println("reading " + minValue + " to " + maxValue);
 
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);
         
        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("id, firstName, lastName, birthdate");
        queryProvider.setFromClause("from customer");
        queryProvider.setWhereClause("where id >= " + minValue + " and id < " + maxValue);
        queryProvider.setSortKeys(sortKeys);
         
        JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(this.dataSource);
        reader.setFetchSize(1000);
        reader.setRowMapper(new CustomerRowMapper());
        reader.setQueryProvider(queryProvider);
         
        return reader;
    }


}