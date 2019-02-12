package com.java.node.heartbeat;

public interface IHeartbeatPublisherListener extends IHeartbeatEventListener {
	public void notifyHeartbeatPublisherStopped();

	public void notifyHeartbeatPublisherStarted();

	public void notifyHeartbeatPublisherHeartbeatSent();

	public void notifyHeartbeatPublisherException(Exception erx, HeartbeatBase heartbeat);
}