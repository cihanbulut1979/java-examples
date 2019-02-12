package com.java.node.heartbeat;

import java.sql.DriverManager;

import javax.jms.DeliveryMode;
import javax.jms.TopicConnectionFactory;

public class DependentHeartbeatPublisher extends HeartbeatPublisher {

	private String jdbcDriverClass = null;
	private String jdbcURL = null;
	private String jdbcUser = null;
	private String jdbcPassword = null;

	public DependentHeartbeatPublisher(TopicConnectionFactory topicConnectionFactory, String topicName, long period,
			String message, int deliveryMode) throws HeartbeatInitializationException {
		super(topicConnectionFactory, topicName, period, message, deliveryMode);
	}

	public DependentHeartbeatPublisher(TopicConnectionFactory topicConnectionFactory, String topicName, long period,
			String message) throws HeartbeatInitializationException {
		super(topicConnectionFactory, topicName, period, message);
	}

	public void setDependentParameters(Object[] parameters) throws InvalidDependentParameters {
		if (parameters.length != 4)
			throw new InvalidDependentParameters(
					"Invalid Number Of Parameters:" + parameters.length + ". Should be 4.");
		try {
			jdbcDriverClass = (String) parameters[0];
			jdbcURL = (String) parameters[1];
			jdbcUser = (String) parameters[2];
			jdbcPassword = (String) parameters[3];
		} catch (Exception erx) {
			throw new InvalidDependentParameters("Exception Occured Processing Parameters:" + erx);
		}
	}

	public boolean fireDependency() {
		java.sql.Connection conn = null;
		try {
			Class.forName(jdbcDriverClass);
			if (jdbcUser == null)
				conn = DriverManager.getConnection(jdbcURL);
			else
				conn = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
			return !conn.isClosed();
		} catch (Exception erx) {
			this.fireHeartbeatPublisherException(new PublisherDependencyException(erx));
			return false;
		} finally {
			try {
				conn.close();
			} catch (Exception erx) {
			}
		}
	}

	public static void main(String args[]) {
		try {
			TopicConnectionFactory tcf = (TopicConnectionFactory) getConnectionFactory(args[0], args[1], args[2],
					args[3], args[4], args[5]);
			DependentHeartbeatPublisher publisher = new DependentHeartbeatPublisher(tcf, args[6],
					Long.parseLong(args[7]), "TEST", DeliveryMode.NON_PERSISTENT);
			publisher.setDependentParameters(new Object[] { "com.pointbase.jdbc.jdbcUniversalDriver",
					"jdbc:pointbase://localhost/sample", "PUBLIC", "PUBLIC" });
			TestPublisherListener tbl = new TestPublisherListener(publisher);
			publisher.registerListener(tbl);
			publisher.startHeartbeatProcess();
		} catch (Exception erx) {
			System.err.println("Error:" + erx);
			System.exit(1);
		}
	}
}
