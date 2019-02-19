package com.java.kafka.java.client.consumer;

import java.util.Arrays;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;

import com.java.kafka.java.client.common.KafkaConstants;
import com.java.kafka.java.client.common.MPAKafkaConstants;

public class MPAKafkaConsumerFactory {
	public void execute(int partitionNo) {

		// Partitions to which a consumer has to assign
		TopicPartition partition = new TopicPartition(MPAKafkaConstants.TOPIC_NAME, partitionNo);

		// This will start a consumer in new thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				MPAKafkaConsumer mpaKafkaConsumer = new MPAKafkaConsumer(KafkaConstants.KAFKA_BROKERS);

				mpaKafkaConsumer.subscribe(Arrays.asList(partition));

				KafkaConsumer<String, String> consumer = mpaKafkaConsumer.getConsumer();

				try {
					while (true) {
						ConsumerRecords records = consumer.poll(KafkaConstants.MAX_POLL_RECORDS);
						for (Object rec : records) {
							ConsumerRecord record = (ConsumerRecord) rec;
							System.out.println(String.format(
									"Consumer : " + " Group : %s "
											+ " Topic : %s, Partition : %d,  Offset : %d, Key : %s Value : %s",
									MPAKafkaConstants.GROUP_ID_CONFIG, record.topic(), record.partition(),
									record.offset(), record.key(), record.value()));

							// processRecord(record);
							// storeRecordInDB(record);
							// storeOffsetInDB(record.topic(),
							// record.partition(), record.offset());

						}

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
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						consumer.commitSync();

						// But if we are closing, there is no “next commit.”
						// We call commitSync(), because it will retry until it
						// succeeds
						// or suffers unrecoverable failure.
					} finally {
						consumer.close();
					}
				}

			}
		}).start();

	}
}
