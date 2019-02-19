package com.java.kafka.java.client.test;

import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import com.java.kafka.java.client.common.KafkaConstants;
import com.java.kafka.java.client.consumer.MPAKafkaConsumer;

public class MPAConsumerStarter {
	public static void main(String[] args) {

		// Partitions to which a consumer has to assign
		TopicPartition partition = new TopicPartition(KafkaConstants.TOPIC_NAME, 0);

		// This will start a consumer in new thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				MPAKafkaConsumer mpaKafkaConsumer = new MPAKafkaConsumer(KafkaConstants.KAFKA_BROKERS,
						KafkaConstants.GROUP_ID_CONFIG);

				mpaKafkaConsumer.subscribe(Arrays.asList(partition));

				KafkaConsumer<String, String> consumer = mpaKafkaConsumer.getConsumer();

				try {
					while (true) {
						ConsumerRecords records = consumer.poll(KafkaConstants.MAX_POLL_RECORDS);
						for (Object rec : records) {
							ConsumerRecord record = (ConsumerRecord) rec;
							System.out.println(String.format("Consumer : Topic - %s, Partition - %d, Key - %s Value: %s", record.topic(),
									record.partition(), record.key(), record.value()));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					consumer.close();
				}

			}
		}).start();

	}
}
