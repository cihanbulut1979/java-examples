package com.java.node.heartbeat;

public interface IHeartbeatPublisher {

	public static final int HB_STARTED = 0;
	public static final int HB_STOPPED = 1;
	public static final int HB_SENT = 2;

	public long getTimeToLive();

	public void setTimeToLive(long time);

	public void fireHeartbeatPublisherStopped();

	public void fireHeartbeatPublisherStarted();

	public void fireHeartbeatPublisherException(Exception erx);

	public void fireHeartbeatSent();

	public boolean fireDependency();

	public void setDependentParameters(Object[] parameters) throws InvalidDependentParameters;

}
