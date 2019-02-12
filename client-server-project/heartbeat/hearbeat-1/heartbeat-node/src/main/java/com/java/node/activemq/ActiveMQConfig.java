package com.java.node.activemq;

import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQConfig implements ExceptionListener {

	private Logger logger = LoggerFactory.getLogger(ActiveMQConfig.class);

	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private String brokerURL;

	public ActiveMQConfig(String brokerURL) {
		this.brokerURL = brokerURL;

		connectionFactory = new ActiveMQConnectionFactory(this.brokerURL);
		
		try {
			tryConnect();
		} catch (JMSException e) {
			e.printStackTrace();
		}

		checkConnection();

	}

	public void tryConnect() throws JMSException {

		connection = connectionFactory.createConnection();
		connection.start();

		connection.setExceptionListener(this);

		logger.error("ActiveMQ connection is scuccessful , broker : " + brokerURL);

	}

	public synchronized void onException(JMSException ex) {
		System.out.println("JMS Exception occured.  Shutting down client. , broker : " + brokerURL);
	}

	public ActiveMQConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public Connection getConnection() {
		return connection;
	}

	public void checkConnection() {
		Thread checker = new Thread("ActiveMQConfig Connection Runner") {
			
			public void run() {

				while (true) {
					if (connection == null) {
						logger.info("ActiveMQ connection not established , broker " + brokerURL);

						try {
							tryConnect();
						} catch (JMSException e) {
							logger.error("Error while connecting ActiveMQ  , broker : " + brokerURL, e);
						}

					}
					
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		};

		checker.start();
	}

}
