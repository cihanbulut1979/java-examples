package com.java.kafka.java.client.test;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.java.kafka.java.client.consumer.DefaultKafkaConsumer;

public class DefaultKafkaConsumerTest {

	public static String KAFKA_BROKERS = "localhost:9092";
	public static String OFFSET_RESET_LATEST = "latest";
	public static String OFFSET_RESET_EARLIER = "earliest";
	public static Integer MAX_POLL_RECORDS = 100;
	public static String TOPIC_NAME = "topic-2";
	public static String GROUP_ID_CONFIG = "consumer-2";

	public static void main(String[] args) {

		Properties config = new Properties();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID_CONFIG);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		config.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		// props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
		// KafkaConstants.MAX_POLL_RECORDS);
		// props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
		// KafkaConstants.OFFSET_RESET_EARLIER);

		List<String> topics = Collections.singletonList(TOPIC_NAME);
		
		DefaultKafkaConsumer kafkaConsumer = new DefaultKafkaConsumer(config, topics);

		new Thread(kafkaConsumer).start();

		// bu consumer dan 2 adet çalýþýrsa 0 nolu partition ayný 2 adet
		// consumer çalýlþýr ve her consumer a farklý partition datsý düþer

	}
}
