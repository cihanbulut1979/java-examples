package com.java.kafka.java.client.test;

import com.java.kafka.java.client.consumer.MPAKafkaConsumer;

public class MPAKafkaConsumerTest {
	public static void main(String[] args) {

		MPAKafkaConsumer kafkaConsumer = new MPAKafkaConsumer();

		kafkaConsumer.execute(0);
		//bu consumer dan 2 adet �al���rsa 0 nolu partition ayn� 2 adet consumer �al�l��r ve ayn� dataalr� okur

	}
}
