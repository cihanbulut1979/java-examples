package com.java.kafka.java.client.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerRecord;

public class DefaultKafkaProducer extends DefaultAbstractProducer<String, String> {

	public DefaultKafkaProducer(Properties config) {
		super(config);
	}

	public void process(ProducerRecord<String, String> record) {

		System.out.println(String.format("Producer : " + " Topic : %s, Partition : %d,  Key : %s Value : %s",
				record.topic(), record.partition(), record.key(), record.value()));

		// processRecord(record);
		// storeRecordInDB(record);
		// storeOffsetInDB(record.topic(),
		// record.partition(), record.offset());

	}
}
