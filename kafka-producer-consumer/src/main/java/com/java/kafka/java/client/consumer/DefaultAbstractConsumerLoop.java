package com.java.kafka.java.client.consumer;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;

public abstract class DefaultAbstractConsumerLoop<K, V> implements Runnable {
	private final KafkaConsumer<K, V> consumer;
	private final List<String> topics;
	private final AtomicBoolean shutdown;
	private final CountDownLatch shutdownLatch;

	public DefaultAbstractConsumerLoop(Properties config, List<String> topics) {
		this.consumer = new KafkaConsumer<>(config);
		this.topics = topics;
		this.shutdown = new AtomicBoolean(false);
		this.shutdownLatch = new CountDownLatch(1);
	}

	public KafkaConsumer<K, V> getConsumer() {
		return consumer;
	}

	public abstract void process(ConsumerRecord<K, V> record);

	public void run() {
		try {
			consumer.subscribe(topics);

			while (!shutdown.get()) {
				ConsumerRecords<K, V> records = consumer.poll(500);
				records.forEach(record -> process(record));

				consumer.commitAsync(new OffsetCommitCallback() {
					public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception e) {
						if (e != null)
							System.out.println("Commit failed for offsets {}" + offsets + " " + e.getMessage());
					}
				});

				// commitDbConnection

				// Here is how we would use commitSync to commit offsets
				// after we finished processing
				// the latest batch of messages:

				// We send the commit and carry on, but if the commit
				// fails
				// , the failure and the offsets will be logged.

				// While everything is fine, we use commitAsync.
				// It is faster, and if one commit fails, the next
				// commit will serve as a retry.

			}
		} finally {
			consumer.close();
			shutdownLatch.countDown();
		}
	}

	public void shutdown() throws InterruptedException {
		shutdown.set(true);
		shutdownLatch.await();
	}
}
