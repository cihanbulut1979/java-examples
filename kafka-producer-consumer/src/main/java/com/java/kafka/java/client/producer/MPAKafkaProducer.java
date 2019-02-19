package com.java.kafka.java.client.producer;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.java.kafka.java.client.common.KafkaConstants;

public class MPAKafkaProducer {

	private Properties props;

	private KafkaProducer kafkaProducer;

	public MPAKafkaProducer(String brokerString) {
		props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKERS);
		//props.put(ProducerConfig.CLIENT_ID_CONFIG, KafkaConstants.CLIENT_ID);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		kafkaProducer = new KafkaProducer(props);
	}

	public void send(ProducerRecord record) {
		kafkaProducer.send(record);
	}

}
