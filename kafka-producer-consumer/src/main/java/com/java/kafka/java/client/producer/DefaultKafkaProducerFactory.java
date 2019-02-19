package com.java.kafka.java.client.producer;

import org.apache.kafka.clients.producer.ProducerRecord;

import com.java.kafka.java.client.common.DefaultKafkaConstants;
import com.java.kafka.java.client.common.KafkaConstants;

public class DefaultKafkaProducerFactory {
	
	public void produce(String message) {

		DefaultKafkaProducer mpaKafkaProducer = new DefaultKafkaProducer(KafkaConstants.KAFKA_BROKERS);

		ProducerRecord record = new ProducerRecord(DefaultKafkaConstants.TOPIC_NAME, message);

		mpaKafkaProducer.send(record);

		System.out.println("Message Sent : Topic : " + DefaultKafkaConstants.TOPIC_NAME + " Message : " + message);
	}
	
	
	public void produce(String key, String message) {

		DefaultKafkaProducer mpaKafkaProducer = new DefaultKafkaProducer(KafkaConstants.KAFKA_BROKERS);

		ProducerRecord record = new ProducerRecord(DefaultKafkaConstants.TOPIC_NAME, key, message);

		mpaKafkaProducer.send(record);

		System.out.println("Message Sent : Topic : " + DefaultKafkaConstants.TOPIC_NAME + " Key : " + key + " Message : " + message);
	}
	
}
