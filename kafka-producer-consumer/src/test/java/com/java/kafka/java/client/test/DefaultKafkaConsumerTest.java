package com.java.kafka.java.client.test;

import com.java.kafka.java.client.consumer.DefaultKafkaConsumer;

public class DefaultKafkaConsumerTest {
	public static void main(String[] args) {

		DefaultKafkaConsumer kafkaConsumer = new DefaultKafkaConsumer();

		kafkaConsumer.execute();
		
		//bu consumer dan 2 adet �al���rsa 0 nolu partition ayn� 2 adet consumer �al�l��r ve her consumer a farkl� partition dats� d��er

	}
}
