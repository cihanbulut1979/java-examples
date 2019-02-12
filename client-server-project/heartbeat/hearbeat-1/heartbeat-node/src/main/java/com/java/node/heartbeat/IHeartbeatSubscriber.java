package com.java.node.heartbeat;

import javax.jms.Message;

public interface IHeartbeatSubscriber {

	public static final int HB_STARTED = 0;
	public static final int HB_STOPPED = 1;
	public static final int HB_RECEIVED = 2;

	public boolean isConnected();

	public void onMessage(Message message);

	public void fireHeartbeatResumed();

	public void fireHeartbeatSubscriberStarted();

	public void fireHeartbeatSubscriberStopped();

	public void fireHeartbeatFailed();

	public void fireHeartbeatReceived();

	public void fireHeartbeatSubscriberException(Exception erx);

}