package com.java.kafka.java.client.test;

import org.apache.kafka.clients.producer.ProducerRecord;

import com.java.kafka.java.client.common.KafkaConstants;
import com.java.kafka.java.client.producer.MPAKafkaProducer;

public class MPAProducerStarter {
	public static void main(String[] args) {

		for (int i = 0; i < 100; i++) {

			int partition = 0;
			String key = i + "";
			String message = "Test Message : " + i;

			MPAKafkaProducer mpaKafkaProducer = new MPAKafkaProducer(KafkaConstants.KAFKA_BROKERS);

			ProducerRecord record = new ProducerRecord(KafkaConstants.TOPIC_NAME, partition, key, message);

			mpaKafkaProducer.send(record);

			System.out.println("Message Sent : " + message + " Key : " + key + " to Partition : " + partition);

		}
	}
}
