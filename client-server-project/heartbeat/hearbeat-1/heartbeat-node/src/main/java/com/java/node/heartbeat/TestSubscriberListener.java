package com.java.node.heartbeat;

import java.util.Date;

public class TestSubscriberListener implements IHeartbeatSubscriberListener {

	public TestSubscriberListener() {
	}

	public void notifyHeartbeatFailedEvent(int ticks) {
		print("Heartbeat Session Failed " + ticks + " times.");
	}

	public void notifyHeartbeatReceived() {
		print("Heartbeat Received");
	}

	public void notifyHeartbeatSubscriberStopped() {
		print("Heartbeat Subscriber Stopped");
	}

	public void notifyHeartbeatSubscriberStarted() {
		print("Heartbeat Subscriber Started");
	}

	public void notifyHeartbeatSubscriberException(Exception erx, HeartbeatBase heartbeat) {
		print("Subscriber Exception:" + erx);
	}

	public void notifyHeartbeatResumed(long time, int ticks) {
		print("Heartbeat Session Resumed at " + new Date(time) + " and " + ticks + " ticks.");
	}

	public void print(String message) {
		System.out.println("[" + new Date() + "] " + message);
	}
}