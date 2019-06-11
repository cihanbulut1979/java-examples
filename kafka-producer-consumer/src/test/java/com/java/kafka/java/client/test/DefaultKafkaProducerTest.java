package com.java.kafka.java.client.test;

import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.java.kafka.java.client.producer.DefaultKafkaProducer;

public class DefaultKafkaProducerTest {

	public static String KAFKA_BROKERS = "localhost:9092";
	public static String OFFSET_RESET_LATEST = "latest";
	public static String OFFSET_RESET_EARLIER = "earliest";
	public static Integer MAX_POLL_RECORDS = 100;
	public static String TOPIC_NAME = "topic-2";
	public static String GROUP_ID_CONFIG = "consumer-2";

	public static void main(String[] args) {

		Properties config = new Properties();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		// props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
		// KafkaConstants.MAX_POLL_RECORDS);
		// props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
		// KafkaConstants.OFFSET_RESET_EARLIER);

		DefaultKafkaProducer mpaKafkaProducer = new DefaultKafkaProducer(config);

		for (int i = 0; i < 200000; i++) {

			String key = i + "";
			String value = "Test Message : " + i;

			ProducerRecord<String, String> data = new ProducerRecord<String, String>(TOPIC_NAME, value);

			mpaKafkaProducer.send(data);

		}
	}
}
