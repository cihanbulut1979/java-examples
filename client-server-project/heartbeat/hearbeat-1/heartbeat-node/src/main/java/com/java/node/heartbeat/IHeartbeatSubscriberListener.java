package com.java.node.heartbeat;

public interface IHeartbeatSubscriberListener extends IHeartbeatEventListener {
	public void notifyHeartbeatFailedEvent(int ticks);

	public void notifyHeartbeatReceived();

	public void notifyHeartbeatSubscriberStopped();

	public void notifyHeartbeatSubscriberStarted();

	public void notifyHeartbeatSubscriberException(Exception erx, HeartbeatBase heartbeat);

	public void notifyHeartbeatResumed(long time, int ticks);
}