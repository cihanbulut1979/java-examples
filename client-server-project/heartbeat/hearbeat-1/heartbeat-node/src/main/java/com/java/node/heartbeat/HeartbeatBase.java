package com.java.node.heartbeat;

import java.util.Hashtable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.jms.ConnectionFactory;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import sun.nio.ch.ThreadPool;

public abstract class HeartbeatBase extends Thread {

	protected TopicConnectionFactory topicConnectionFactory = null;
	protected TopicConnection topicConnection = null;
	protected TopicSession topicSession = null;
	protected Topic topic = null;

	protected boolean isUsingConnFactory;

	protected String topicName = null;

	protected String message = null;

	protected long period = -1;

	protected boolean run = true;

	private boolean tick = true;

	protected Hashtable listeners = null;

	protected boolean process = true;

	ThreadPoolExecutor threadPool = null;

	public HeartbeatBase() {
	}

	public HeartbeatBase(TopicConnectionFactory topicConnectionFactory, String topicName, long period, String message)
			throws HeartbeatInitializationException {
		this.topicConnectionFactory = topicConnectionFactory;
		this.topicName = topicName;
		this.period = period;
		this.message = message;
		threadPool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

		initBase();
	}

	public HeartbeatBase(TopicConnection topicConnection, String topicName, long period, String message)
			throws HeartbeatInitializationException {
		this.topicConnection = topicConnection;
		this.topicName = topicName;
		this.period = period;
		this.message = message;
		threadPool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		
		initBase(topicConnection);
	}

	protected void initBase() throws HeartbeatInitializationException {
		listeners = new Hashtable();
		if (topicConnectionFactory == null)
			throw new HeartbeatInitializationException("Invalid Connection Factory");
		try {
			topicConnection = topicConnectionFactory.createTopicConnection();
			topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			topic = topicSession.createTopic(topicName);
			isUsingConnFactory = true;
			// init();

		} catch (Exception erx) {
			throw new HeartbeatInitializationException("Exception Initializing:" + erx.getMessage());
		} finally {
			regShutdownHook();
		}
	}

	protected void initBase(TopicConnection connection) throws HeartbeatInitializationException {
		listeners = new Hashtable();
		try {
			topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			topic = topicSession.createTopic(topicName);
			isUsingConnFactory = false;
			// init();
		} catch (Exception erx) {
			throw new HeartbeatInitializationException("Exception Initializing:" + erx.getMessage());
		} finally {
			regShutdownHook();
		}
	}

	protected void destroyBase() {
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
		threadPool.shutdownNow();

	}

	public void registerListener(IHeartbeatEventListener listener) throws InvalidEventListener {
		if (this instanceof HeartbeatSubscriber) {
			if (!(listener instanceof IHeartbeatSubscriberListener)) {
				throw new InvalidEventListener(
						"Invalid Listener Type For " + this.getClass().getName() + ":" + listener.getClass().getName());
			}
		} else if (this instanceof HeartbeatPublisher) {
			if (!(listener instanceof IHeartbeatPublisherListener)) {
				throw new InvalidEventListener(
						"Invalid Listener Type For " + this.getClass().getName() + ":" + listener.getClass().getName());
			}
		}
		listeners.put(new Integer(listener.hashCode()), listener);
	}

	protected abstract void regShutdownHook();

	protected abstract void init() throws HeartbeatInitializationException;

	protected void interruptProcess() {
		this.interrupt();
	}

	public void unregisterListener(IHeartbeatEventListener listener) {
		listeners.remove(new Integer(listener.hashCode()));
	}

	public void startHeartbeatProcess() {
		run = true;
		this.start();
	}

	public void stopHeartbeatProcess() {
		run = false;
		process = false;
		interruptProcess();
	}

	public void setMessagesPaused(boolean working) {
		process = working;
	}

	public boolean isProcessingMessages() {
		return process;
	}

	public synchronized boolean isTick() {
		return tick;
	}

	public synchronized void setTick(boolean b) {
		tick = b;
	}

	public long getPeriod() {
		return period;
	}

	public static ConnectionFactory getConnectionFactory(String url, String principal, String credential,
			String factoryName, String authType, String jmsFactoryName) throws NamingException {
		Hashtable env = new Hashtable();
		env.put(Context.SECURITY_AUTHENTICATION, authType);
		env.put(Context.INITIAL_CONTEXT_FACTORY, factoryName);
		env.put(Context.PROVIDER_URL, url);
		env.put(Context.SECURITY_PRINCIPAL, principal);
		env.put(Context.SECURITY_CREDENTIALS, credential);
		Context ctx = new InitialContext(env);
		Object obj = ctx.lookup(jmsFactoryName);
		return (ConnectionFactory) obj;
	}

}