package com.java.kafka.java.client.producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import com.java.kafka.java.client.common.KafkaConstants;

public class DefaultKafkaProducer {

	private Properties props;

	private KafkaProducer kafkaProducer;

	public DefaultKafkaProducer(String brokerString) {
		props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKERS);
		// props.put(ProducerConfig.CLIENT_ID_CONFIG, KafkaConstants.CLIENT_ID);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		kafkaProducer = new KafkaProducer(props);
	}

	public void send(ProducerRecord record) {
		kafkaProducer.send(record);
	}

	public void sendSync(ProducerRecord record) throws InterruptedException, ExecutionException {
		long time = System.currentTimeMillis();

		RecordMetadata metadata = (RecordMetadata) kafkaProducer.send(record).get();

		long elapsedTime = System.currentTimeMillis() - time;
		System.out.printf("sent record(key=%s value=%s) " + "meta(partition=%d, offset=%d) time=%d\n", record.key(),
				record.value(), metadata.partition(), metadata.offset(), elapsedTime);

	}

}
