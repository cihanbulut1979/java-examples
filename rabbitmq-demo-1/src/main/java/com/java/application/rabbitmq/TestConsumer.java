package com.java.application.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class TestConsumer {
	private final static String QUEUE_NAME = "test-queue";
	private final static String EXCHANGE_NAME = "test-exchange";

	private final static String HOST_NAME = "10.10.10.183";
	private final static int PORT = 5672;
//		private final static String[] HOST_NAME = {"172.16.80.182", "172.16.80.183"}
	private final static String USER_NAME = "user";
	private final static String PASSWORD = "bitnami";

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

	public void receive(final String name) {
		Thread t = new Thread(new Runnable() {
			private Connection connection = null;
			private Channel channel = null;
			private String queueName = null;

			public void run() {
				try {
					connection = connect();
					channel = connection.createChannel();

					boolean durable = true;
					// without exchange
					channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

					// String queueName = channel.queueDeclare().getQueue();

					// When RabbitMQ quits or crashes it will forget the queues and messages
					// unless set durable = true

					Consumer consumer = new DefaultConsumer(channel) {
						@Override
						public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
							String message = new String(body, "UTF-8");
							System.out.println(name + " Received " + envelope.getRoutingKey() + ": '" + message + "'");
						}
					};

					// auto acknowledgment is true
					// if false, will result in messages_unacknowledged
					// `rabbitmqctl list_queues name messages_ready messages_unacknowledged`

					System.out.println("Consumer " + name + " ready to consume");

					while (true) {
						channel.basicConsume(QUEUE_NAME, true, consumer);
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						channel.queueDelete(queueName);
						channel.close();
						connection.close();
					} catch (Exception e) {

					}
					System.out.println(name + " thread exists!");
				}
			}
		});

		t.start();
	}

	public void receive1(final String type, final String name, final String bindingKey) {
		Thread t = new Thread(new Runnable() {
			private Connection connection = null;
			private Channel channel = null;
			private String queueName = null;

			public void run() {
				try {
					connection = connect();
					channel = connection.createChannel();

					// without exchange
					// channel.queueDeclare(QUEUE_NAME, false, false, false, null);

					// with exchange, create queue for each subscriber and bind to exchange
					// direct supports routing key, fanout doesn't support
					channel.exchangeDeclare(EXCHANGE_NAME, type);
					// String queueName = channel.queueDeclare().getQueue();

					// When RabbitMQ quits or crashes it will forget the queues and messages
					// unless set durable = true
					boolean durable = true;
					queueName = channel.queueDeclare("", durable, false, false, null).getQueue();
					channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);

					Consumer consumer = new DefaultConsumer(channel) {
						@Override
						public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
							String message = new String(body, "UTF-8");
							System.out.println(name + " Received " + envelope.getRoutingKey() + ": '" + message + "'");
						}
					};

					// auto acknowledgment is true
					// if false, will result in messages_unacknowledged
					// `rabbitmqctl list_queues name messages_ready messages_unacknowledged`

					while (true) {
						channel.basicConsume(queueName, true, consumer);
					}

				} catch (Exception e) {
				} finally {
					try {
						channel.queueDelete(queueName);
						channel.close();
						connection.close();
					} catch (Exception e) {

					}
					System.out.println(name + " thread exists!");
				}
			}
		});

		t.start();
	}

	public static void main(String[] args) {
		try {
			TestConsumer q = new TestConsumer();

			for (int i = 0; i < 10; i++) {
				q.receive("subscriber-" + i);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
