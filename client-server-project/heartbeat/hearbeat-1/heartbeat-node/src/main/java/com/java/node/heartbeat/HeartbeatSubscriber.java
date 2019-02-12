package com.java.node.heartbeat;

import java.util.Enumeration;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.java.node.activemq.ActiveMQConfig;
import com.java.node.activemq.ActiveMQSessionManager;

public class HeartbeatSubscriber extends HeartbeatBase
		implements IHeartbeatSubscriber, ExceptionListener, MessageListener {

	protected TopicSubscriber topicSubscriber = null;

	protected long resumeTime = 0;

	protected long failTime = 0;

	protected boolean state = true;

	protected int fTickCount = 0;

	public HeartbeatSubscriber(TopicConnectionFactory topicConnectionFactory, String topicName, long period,
			String message) throws HeartbeatInitializationException {
		super(topicConnectionFactory, topicName, period, message);
		init();
	}

	public HeartbeatSubscriber(TopicConnection topicConnection, String topicName, long period, String message)
			throws HeartbeatInitializationException {
		super(topicConnection, topicName, period, message);
		init();
	}

	protected void init() throws HeartbeatInitializationException {
		try {
			topicSubscriber = topicSession.createSubscriber(topic);
			this.topicConnection.setExceptionListener(this);
		} catch (Exception erx) {
			throw new HeartbeatInitializationException("TopicSubscriber Exception:" + erx.getMessage());
		}
	}

	public boolean isConnected() {
		return !state;
	}

	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				if (((TextMessage) message).getText().equals(topicName + " HEARTBEAT")) {
					setTick(true);
					if (state) { // recovering from heartbeat failure
						state = false;
						resumeTime = System.currentTimeMillis() - failTime;
						fireHeartbeatResumed();
						fTickCount = 0;
					}
					fireHeartbeatReceived();
				}
			}
		} catch (Exception erx) {
			setTick(false);
			this.fireHeartbeatSubscriberException(erx);
		}
	}

	public void fireHeartbeatResumed() {
		Runnable runnable = new Runnable() {
			public void run() {
				Enumeration enum1 = listeners.keys();
				while (enum1.hasMoreElements()) {
					Integer i = (Integer) enum1.nextElement();
					IHeartbeatSubscriberListener listener = (IHeartbeatSubscriberListener) listeners.get(i);
					listener.notifyHeartbeatResumed(resumeTime, fTickCount);
				}
			}
		};
		try {
			threadPool.submit(runnable);
		} catch (Exception cex) {
			fireHeartbeatSubscriberException(
					new Exception("Closed Event Thread Pool Exception On fireHeartbeatResumed()"));
		}
	}

	public void fireHeartbeatSubscriberStarted() {
		fireEvent(IHeartbeatSubscriber.HB_STARTED, true);
	}

	public void fireHeartbeatSubscriberStopped() {
		fireEvent(IHeartbeatSubscriber.HB_STOPPED, false);
	}

	public void fireHeartbeatFailed() {
		Runnable runnable = new Runnable() {
			public void run() {
				Enumeration enum1 = listeners.keys();
				while (enum1.hasMoreElements()) {
					Integer i = (Integer) enum1.nextElement();
					IHeartbeatSubscriberListener listener = (IHeartbeatSubscriberListener) listeners.get(i);
					listener.notifyHeartbeatFailedEvent(fTickCount);
				}
			}
		};
		try {
			threadPool.submit(runnable);
		} catch (Exception cex) {
			fireHeartbeatSubscriberException(
					new Exception("Closed Event Thread Pool Exception On fireHeartbeatSubscriberStopped()"));
		}
	}

	public void fireHeartbeatReceived() {
		fireEvent(IHeartbeatSubscriber.HB_RECEIVED, true);
	}

	public void fireHeartbeatSubscriberException(Exception erx) {
		Enumeration enum1 = listeners.keys();
		while (enum1.hasMoreElements()) {
			Integer i = (Integer) enum1.nextElement();
			IHeartbeatSubscriberListener listener = (IHeartbeatSubscriberListener) listeners.get(i);
			listener.notifyHeartbeatSubscriberException(erx, this);
		}
	}

	public void run() {
		try {
			setTick(true); // this is true so that the first heartbeat received
							// is not a resume. This will result in a slight
							// delay in a heartbeat failing on startup. Needs
							// work. NJW Feb 5 2002
			topicSubscriber.setMessageListener(this);
			topicConnection.start();
			fireHeartbeatSubscriberStarted();
			while (run) {
				this.sleep(period);
				if (run && process) {
					if (!isTick()) { // heartbeat failed
						failTime = System.currentTimeMillis();
						state = true;
						fTickCount++;
						fireHeartbeatFailed();
					} else { // heartbeat received
						setTick(false);
						state = false;
					}
				}
			}
			fireHeartbeatSubscriberStopped();
			shutdown();
		} catch (Exception erx) {
			if (run)
				fireHeartbeatSubscriberException(erx);
			else
				fireHeartbeatSubscriberStopped();
			shutdown();
		}
	}

	public String toString() {
		return "Consumer:" + topic + ":" + (run ? "Running" : "Stopped") + ":" + (process ? "Processing" : "Stalled");
	}

	private void shutdown() {
		try {
			topicSubscriber.close();
		} catch (Exception e) {
		}
		try {
			topicSession.close();
		} catch (Exception e) {
		}
		try {
			if (isUsingConnFactory)
				topicConnection.stop();
		} catch (Exception e) {
		}
		try {
			if (isUsingConnFactory)
				topicConnection.close();
		} catch (Exception e) {
		}
	}

	protected void regShutdownHook() {
		try {
			Thread th = new Thread() {
				public void run() {
					System.out.println("Shutdown Hook Activated On Instance Of Heartbeat Consumer.");
					run = false;
					try {
						topicSubscriber.close();
					} catch (Exception e) {
					}
					try {
						topicSession.close();
					} catch (Exception e) {
					}
					try {
						if (isUsingConnFactory)
							topicConnection.stop();
					} catch (Exception e) {
					}
					try {
						if (isUsingConnFactory)
							topicConnection.close();
					} catch (Exception e) {
					}
				}
			};
			th.setDaemon(true);
			// Runtime.getRuntime().addShutdownHook(th);
		} catch (java.lang.NoSuchMethodError erx) {
			System.err.println("Unable To Register Shutdown Hook For HeartbeatSubscriber");
		}
	}

	public void onException(JMSException exception) {
		fireHeartbeatSubscriberException(exception);
	}

	protected void fireEvent(final int eventID, boolean asynch) {
		Runnable runnable = new Runnable() {
			public void run() {
				Enumeration enum1 = listeners.keys();
				while (enum1.hasMoreElements()) {
					Integer i = (Integer) enum1.nextElement();
					IHeartbeatSubscriberListener listener = (IHeartbeatSubscriberListener) listeners.get(i);
					switch (eventID) {
					case IHeartbeatSubscriber.HB_STARTED:
						listener.notifyHeartbeatSubscriberStarted();
						break;
					case IHeartbeatSubscriber.HB_STOPPED:
						listener.notifyHeartbeatSubscriberStopped();
						break;
					case IHeartbeatSubscriber.HB_RECEIVED:
						listener.notifyHeartbeatReceived();
						break;
					default:
						fireHeartbeatSubscriberException(
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
				fireHeartbeatSubscriberException(
						new Exception("Heartbeat Subscriber Event Exception(" + eventID + "):" + cex));
			}
		} else {
			runnable.run();
		}
	}

	public static void main(String args[]) {
		try {
			
			ActiveMQConfig activeMQConfig = new ActiveMQConfig("tcp://localhost:61616?jms.blobTransferPolicy.defaultUploadUrl=http://localhost:8161/fileserver/");
			
			ActiveMQSessionManager activeMQSessionManager = new ActiveMQSessionManager(activeMQConfig);
			
			ActiveMQConnectionFactory activeMQConnectionFactory =  activeMQSessionManager.getActiveMQConfig().getConnectionFactory();
						
			/*TopicConnectionFactory tcf = (TopicConnectionFactory) getConnectionFactory(args[0], args[1], args[2],
					args[3], args[4], args[5]);*/
			HeartbeatSubscriber subscriber = new HeartbeatSubscriber(activeMQConnectionFactory, "Topic-1", 1000, "TEST");
			TestSubscriberListener tbl = new TestSubscriberListener();
			subscriber.registerListener(tbl);
			subscriber.startHeartbeatProcess();
		} catch (Exception erx) {
			System.err.println("Error:" + erx);
			System.exit(1);
		}
	}

}