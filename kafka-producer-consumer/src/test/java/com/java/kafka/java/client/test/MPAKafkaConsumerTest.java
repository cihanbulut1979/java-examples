package com.java.kafka.java.client.test;

import com.java.kafka.java.client.consumer.MPAKafkaConsumerFactory;

public class MPAKafkaConsumerTest {
	public static void main(String[] args) {

		MPAKafkaConsumerFactory kafkaConsumer = new MPAKafkaConsumerFactory();

		kafkaConsumer.execute(0);
		//bu consumer dan 2 adet çalýþýrsa 0 nolu partition ayný 2 adet consumer çalýlþýr ve ayný dataalrý okur

	}
}
