package com.java.kafka.java.client.test;

import com.java.kafka.java.client.producer.MPAKafkaProducerExecutor;

public class MPAKafkaProducerTest {
	public static void main(String[] args) {

		for (int i = 0; i < 100; i++) {

			int partition = 0;
			String key = i + "";
			String message = "Test Message : " + i;

			MPAKafkaProducerExecutor mpaKafkaProducer = new MPAKafkaProducerExecutor();

			mpaKafkaProducer.produce(partition, key, message);

		}
	}
}
