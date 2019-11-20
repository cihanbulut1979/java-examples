package com.java.application.rabbitmq;

import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class TestPublisher {
	private final static String QUEUE_NAME = "test-queue";
	private final static String EXCHANGE_NAME = "test-exchange";

	private final static String HOST_NAME = "10.10.10.183";
	private final static int PORT = 5672;
//		private final static String[] HOST_NAME = {"172.16.80.182", "172.16.80.183"}
	private final static String USER_NAME = "user";
	private final static String PASSWORD = "bitnami";

	// exchange type: direct and Topic, routing keys for publisher to send test
	// message
	private final String[] routingKeyDirect = { "error", "debug", "info" };
	private final String[] routingKeyTopic = { "error.app1", "error.app2", "debug.app1", "debug.app2", "info.app1", "info.app2" };

	protected Connection connect() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST_NAME);
		factory.setPort(PORT);
		factory.setUsername(USER_NAME);
		factory.setPassword(PASSWORD);

		Connection connection = null;

		try {
			connection = factory.newConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	// exchange type: fan, direct, topic
	// fan - no routing key, exchange to all queues
	// direct - simple routing key, exchange to binding queues.
	// topic - routing key with * and #, complex binding queues
	public void send(String type, TestBean msg) {

		Connection connection = null;
		Channel channel = null;

		try {
			connection = connect();
			channel = connection.createChannel();

			boolean durable = true;

			// with exchange, can use binding key
			channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

			int i = 0;
			String[] routingKey = type.equals("direct") ? routingKeyDirect : routingKeyTopic;

			// no exchange
			// channel.basicPublish("", QUEUE_NAME, null, newMsg.getBytes());

			// with exchange
			// MessageProperties.PERSISTENT_TEXT_PLAIN tell RabbitMQ to save message on disk

			byte[] messageInBytes = msg.getBytes();

			channel.basicPublish("", QUEUE_NAME, null, messageInBytes);

		} catch (Exception e) {
		} finally {
			try {
				channel.close();
				connection.close();
			} catch (Exception e) {

			}
		}
	}

	// exchange type: fan, direct, topic
	// fan - no routing key, exchange to all queues
	// direct - simple routing key, exchange to binding queues.
	// topic - routing key with * and #, complex binding queues
	public void send1(String type, TestBean msg) {

		Connection connection = null;
		Channel channel = null;

		try {
			connection = connect();
			channel = connection.createChannel();

			// with exchange, can use binding key
			channel.exchangeDeclare(EXCHANGE_NAME, type);

			int i = 0;
			String[] routingKey = type.equals("direct") ? routingKeyDirect : routingKeyTopic;

			// no exchange
			// channel.basicPublish("", QUEUE_NAME, null, newMsg.getBytes());

			// with exchange
			// MessageProperties.PERSISTENT_TEXT_PLAIN tell RabbitMQ to save message on disk

			byte[] messageInBytes = msg.getBytes();

			channel.basicPublish(EXCHANGE_NAME, routingKey[i % routingKey.length], MessageProperties.PERSISTENT_TEXT_PLAIN, messageInBytes);

			System.out.println("Sent " + routingKey[i % routingKey.length] + ": '" + messageInBytes + "'");

		} catch (Exception e) {
		} finally {
			try {
				channel.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		try {
			
			System.out.println("Started : " + new Date());
			
			TestPublisher q = new TestPublisher();

			for (int i = 0; i < 10000000; i++) {
				TestBean bean = new TestBean();

				bean.setMessageId(i + "");
				bean.setPriority(5);

				q.send("topic-1", bean);

				System.out.println("Sent message " + bean);
			}
			
			System.out.println("Finished : " + new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
