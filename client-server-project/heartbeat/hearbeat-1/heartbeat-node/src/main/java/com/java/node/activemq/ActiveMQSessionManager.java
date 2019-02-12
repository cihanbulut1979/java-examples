package com.java.node.activemq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQSessionManager {

	final Logger LOG = LoggerFactory.getLogger(ActiveMQSessionManager.class);

	private ActiveMQConfig activeMQConfig;

	private Connection connection;

	private ActiveMQSession session;

	public ActiveMQSessionManager(ActiveMQConfig activeMQConfig) throws JMSException {

		this.activeMQConfig = activeMQConfig;

		connection = activeMQConfig.getConnection();

		session = (ActiveMQSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	}

	public Connection getConnection() {
		return connection;
	}

	public ActiveMQSession getSession() {
		return session;
	}

	public ActiveMQConfig getActiveMQConfig() {
		return activeMQConfig;
	}

}
