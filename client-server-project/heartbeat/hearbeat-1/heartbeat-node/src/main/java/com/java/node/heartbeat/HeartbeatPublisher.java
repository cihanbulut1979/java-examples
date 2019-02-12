package com.java.node.heartbeat;

import java.util.Enumeration;

import javax.jms.DeliveryMode;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.java.node.activemq.ActiveMQConfig;
import com.java.node.activemq.ActiveMQSessionManager;

public class HeartbeatPublisher extends HeartbeatBase implements IHeartbeatPublisher, ExceptionListener {

	protected TopicPublisher topicPublisher = null;

	protected long timeToLive = 0;

	protected int dMode = DeliveryMode.NON_PERSISTENT;

	public HeartbeatPublisher(TopicConnectionFactory topicConnectionFactory, String topicName, long period,
			String message, int deliveryMode) throws HeartbeatInitializationException {
		super(topicConnectionFactory, topicName, period, message);
		dMode = deliveryMode;
		init();

	}

	public HeartbeatPublisher(TopicConnectionFactory topicConnectionFactory, String topicName, long period,
			String message) throws HeartbeatInitializationException {
		super(topicConnectionFactory, topicName, period, message);
		init();

	}

	public HeartbeatPublisher(TopicConnection topicConnection, String topicName, long period, String message)
			throws HeartbeatInitializationException {
		super(topicConnection, topicName, period, message);
		init();
	}

	public HeartbeatPublisher(TopicConnection topicConnection, String topicName, long period, String message,
			int deliveryMode) throws HeartbeatInitializationException {
		super(topicConnection, topicName, period, message);
		dMode = deliveryMode;
	}

	protected void init() throws HeartbeatInitializationException {
		try {
			topicPublisher = topicSession.createPublisher(topic);
			topicPublisher.setDeliveryMode(dMode);
			topicConnection.setExceptionListener(this);
		} catch (Exception erx) {
			throw new HeartbeatInitializationException("TopicPublisher Exception:" + erx.getMessage());
		}
	}

	protected void regShutdownHook() {
		try {
			Thread th = new Thread() {
				public void run() {
					System.out.println("Shutdown Hook Activated On Instance Of Heartbeat Supplier.");
					run = false;
					try {
						topicPublisher.close();
					} catch (Exception e) {
					}
					try {
						topicSession.close();
					} catch (Exception e) {
					}
					try {
						if (isUsingConnFactory)
							topicConnection.close();
					} catch (Exception e) {
					}
					try {
						if (isUsingConnFactory)
							topicConnection.stop();
					} catch (Exception e) {
					}
				}
			};
			th.setDaemon(true);
			// Runtime.getRuntime().addShutdownHook(th);
		} catch (java.lang.NoSuchMethodError erx) {
			System.err.println("Unable To Register Shutdown Hook For HeartbeatPublisher");
		}
	}

	public long getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(long time) {
		timeToLive = time;
	}

	public void fireHeartbeatPublisherStopped() {
		fireEvent(IHeartbeatPublisher.HB_STOPPED, false);
	}

	public void fireHeartbeatPublisherStarted() {
		fireEvent(IHeartbeatPublisher.HB_STARTED, true);
	}

	public void fireHeartbeatPublisherException(Exception erx) {
		Enumeration enum1 = listeners.keys();
		IHeartbeatPublisherListener listener = null;
		while (enum1.hasMoreElements()) {
			Integer i = (Integer) enum1.nextElement();
			listener = (IHeartbeatPublisherListener) listeners.get(i);
			listener.notifyHeartbeatPublisherException(erx, this);
		}
	}

	public void fireHeartbeatSent() {
		fireEvent(IHeartbeatPublisher.HB_SENT, true);
	}

	public void onException(JMSException exception) {
		fireHeartbeatPublisherException(exception);
	}

	/**
	 * Since this is a dependent publisher, this method is defined as a NO-OP
	 */
	public void setDependentParameters(Object[] parameters) throws InvalidDependentParameters {
	}

	public void run() {
		try {
			TextMessage tmessage = topicSession.createTextMessage();
			tmessage.setText(topicName + " HEARTBEAT");
			fireHeartbeatPublisherStarted();
			while (run) {
				Thread.sleep(period);
				topicPublisher.setTimeToLive(timeToLive);
				if (process) {
					if (fireDependency()) {
						topicPublisher.publish(tmessage);
						fireHeartbeatSent();
					}
				}
			}
			fireHeartbeatPublisherStopped();
			shutdown();
		} catch (Exception erx) {
			if (run)
				fireHeartbeatPublisherException(erx);
			else
				fireHeartbeatPublisherStopped();
			shutdown();
		}
	}

	public void shutdown() {
		try {
			topicPublisher.close();
		} catch (Exception e) {
		}
		destroyBase();
	}

	public String toString() {
		return "Supplier:" + topic + ":" + (run ? "Running" : "Stopped") + ":" + (process ? "Processing" : "Stalled");
	}

	protected void fireEvent(final int eventID, boolean asynch) {
		Runnable runnable = new Runnable() {
			public void run() {
				Enumeration enum1 = listeners.keys();
				while (enum1.hasMoreElements()) {
					Integer i = (Integer) enum1.nextElement();
					IHeartbeatPublisherListener listener = (IHeartbeatPublisherListener) listeners.get(i);
					switch (eventID) {
					case IHeartbeatPublisher.HB_STARTED:
						listener.notifyHeartbeatPublisherStarted();
						break;
					case IHeartbeatPublisher.HB_STOPPED:
						listener.notifyHeartbeatPublisherStopped();
						break;
					case IHeartbeatPublisher.HB_SENT:
						listener.notifyHeartbeatPublisherHeartbeatSent();
						break;
					default:
						fireHeartbeatPublisherException(
								new Exception("Invalid eventID Passed to fireEvent:" + eventID));
						break;
					}
				}
			}
		};
		if (asynch) {
			try {
				threadPool.submit(runnable);
			} catch (Exception cex) {
				fireHeartbeatPublisherException(
						new Exception("Heartbeat Publisher Event Exception(" + eventID + "):" + cex));
			}
		} else {
			runnable.run();
		}
	}

	public boolean fireDependency() {
		return true;
	}

	public static void main(String args[]) {
		try {
			
			ActiveMQConfig activeMQConfig = new ActiveMQConfig("tcp://localhost:61616?jms.blobTransferPolicy.defaultUploadUrl=http://localhost:8161/fileserver/");
			
			ActiveMQSessionManager activeMQSessionManager = new ActiveMQSessionManager(activeMQConfig);
			
			ActiveMQConnectionFactory activeMQConnectionFactory =  activeMQSessionManager.getActiveMQConfig().getConnectionFactory();
			
			/*TopicConnectionFactory tcf = (TopicConnectionFactory) getConnectionFactory(args[0], args[1], args[2],
					args[3], args[4], args[5]);*/
			HeartbeatPublisher publisher = new HeartbeatPublisher(activeMQConnectionFactory, "Topic-1", 1000, "TEST",
					DeliveryMode.NON_PERSISTENT);
			TestPublisherListener tbl = new TestPublisherListener(publisher);
			publisher.registerListener(tbl);
			publisher.startHeartbeatProcess();
		} catch (Exception erx) {
			System.err.println("Error:" + erx);
			System.exit(1);
		}
	}
}