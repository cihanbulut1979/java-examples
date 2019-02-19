package com.java.kafka.java.client.consumer;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class DefaultKafkaConsumer extends DefaultAbstractConsumerLoop<String, String> {

	public DefaultKafkaConsumer(Properties config, List<String> topics) {
		super(config, topics);
	}

	public void process(ConsumerRecord<String, String> record) {

		System.out.println(String.format(
				"Consumer : " + " Group : %s " + " Topic : %s, Partition : %d,  Offset : %d, Key : %s Value : %s",
				"", record.topic(), record.partition(), record.offset(),
				record.key(), record.value()));

		// processRecord(record);
		// storeRecordInDB(record);
		// storeOffsetInDB(record.topic(),
		// record.partition(), record.offset());

	}
}
