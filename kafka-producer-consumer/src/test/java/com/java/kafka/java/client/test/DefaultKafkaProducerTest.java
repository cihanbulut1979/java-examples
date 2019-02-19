package com.java.kafka.java.client.test;

import com.java.kafka.java.client.producer.DefaultKafkaProducerFactory;

public class DefaultKafkaProducerTest {
	public static void main(String[] args) {

		for (int i = 0; i < 100; i++) {

			String key = i + "";
			String message = "Test Message : " + i;

			DefaultKafkaProducerFactory mpaKafkaProducer = new DefaultKafkaProducerFactory();

			mpaKafkaProducer.produce(key, message);

		}
	}
}
