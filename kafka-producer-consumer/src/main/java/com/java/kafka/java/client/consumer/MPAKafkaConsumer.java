package com.java.kafka.java.client.consumer;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.java.kafka.java.client.common.KafkaConstants;
import com.java.kafka.java.client.common.MPAKafkaConstants;

//Manual Partition Assignment  
public class MPAKafkaConsumer {
	private Properties props;
	private KafkaConsumer<String, String> consumer;

	public MPAKafkaConsumer(String brokerString) {

		props = new Properties();

		final Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerString);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, MPAKafkaConstants.GROUP_ID_CONFIG);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		// props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
		// KafkaConstants.MAX_POLL_RECORDS);
		// props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
		// KafkaConstants.OFFSET_RESET_EARLIER);

		consumer = new KafkaConsumer<>(props);

	}

	public void subscribe(List<TopicPartition> topicsPartions) {
		consumer.assign(topicsPartions);
	}

	public Properties getProps() {
		return props;
	}

	public KafkaConsumer<String, String> getConsumer() {
		return consumer;
	}

}
