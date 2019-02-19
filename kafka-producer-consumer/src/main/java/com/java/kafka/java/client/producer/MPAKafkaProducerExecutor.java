package com.java.kafka.java.client.producer;

import org.apache.kafka.clients.producer.ProducerRecord;

import com.java.kafka.java.client.common.DefaultKafkaConstants;
import com.java.kafka.java.client.common.KafkaConstants;
import com.java.kafka.java.client.common.MPAKafkaConstants;

public class MPAKafkaProducerExecutor {
	public void produce(int partitionNo, String key, String message) {

		MPAKafkaProducer mpaKafkaProducer = new MPAKafkaProducer(KafkaConstants.KAFKA_BROKERS);

		ProducerRecord record = new ProducerRecord(MPAKafkaConstants.TOPIC_NAME, partitionNo, key, message);

		mpaKafkaProducer.send(record);

		System.out.println("Message Sent : Topic : " + DefaultKafkaConstants.TOPIC_NAME + " Key : " + key + " Partition : " + partitionNo + " Message : " + message);
	}
	
}
