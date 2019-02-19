package com.java.kafka.java.client.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public abstract class DefaultAbstractProducer<K, V> {
	private final KafkaProducer<K, V> producer;

	public DefaultAbstractProducer(Properties config) {
		this.producer = new KafkaProducer<>(config);
	}

	public KafkaProducer<K, V> getProducer() {
		return producer;
	}

	public void send(ProducerRecord<K, V> record) {
		producer.send(record);

		process(record);

	}

	public abstract void process(ProducerRecord<K, V> record);

}
