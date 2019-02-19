package com.java.kafka.java.client.test;

import com.java.kafka.java.client.consumer.DefaultKafkaConsumerExecutor;

public class DefaultKafkaConsumerTest {
	public static void main(String[] args) {

		DefaultKafkaConsumerExecutor kafkaConsumer = new DefaultKafkaConsumerExecutor();

		kafkaConsumer.execute();
		
		//bu consumer dan 2 adet çalýþýrsa 0 nolu partition ayný 2 adet consumer çalýlþýr ve her consumer a farklý partition datsý düþer

	}
}
