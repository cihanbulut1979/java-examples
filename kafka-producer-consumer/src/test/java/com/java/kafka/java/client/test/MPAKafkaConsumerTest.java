package com.java.kafka.java.client.test;

import com.java.kafka.java.client.consumer.MPAKafkaConsumer;

public class MPAKafkaConsumerTest {
	public static void main(String[] args) {

		MPAKafkaConsumer kafkaConsumer = new MPAKafkaConsumer();

		kafkaConsumer.execute(0);

	}
}
