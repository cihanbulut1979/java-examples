package tr.com.java.spring.batch.remotepartioning;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:remote-partitioning.properties")
public class BrokerConfiguration {

	@Value("${broker.url}")
	private String brokerUrl;

	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(this.brokerUrl);
		connectionFactory.setTrustAllPackages(true);
		return connectionFactory;
	}

}
