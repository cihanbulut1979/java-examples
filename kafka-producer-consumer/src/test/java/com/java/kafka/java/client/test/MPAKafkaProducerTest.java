package com.java.kafka.java.client.test;

import com.java.kafka.java.client.producer.MPAKafkaProducerFactory;

public class MPAKafkaProducerTest {
	public static void main(String[] args) {

		for (int i = 0; i < 100; i++) {

			int partition = 0;
			String key = i + "";
			String message = "Test Message : " + i;

			MPAKafkaProducerFactory mpaKafkaProducer = new MPAKafkaProducerFactory();

			mpaKafkaProducer.produce(partition, key, message);

		}
	}
}
