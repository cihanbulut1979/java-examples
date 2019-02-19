package com.java.kafka.java.client.producer;

import org.apache.kafka.clients.producer.ProducerRecord;

import com.java.kafka.java.client.common.KafkaConstants;

public class MPAKafkaProducer {
	public void produce(int partitionNo, String key, String message) {

		MPAKafkaProducerFactory mpaKafkaProducer = new MPAKafkaProducerFactory(KafkaConstants.KAFKA_BROKERS);

		ProducerRecord record = new ProducerRecord(KafkaConstants.TOPIC_NAME, partitionNo, key, message);

		mpaKafkaProducer.send(record);

		System.out.println("Message Sent : " + message + " Key : " + key + " to Partition : " + partitionNo);
	}
}
