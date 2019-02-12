package com.java.node.heartbeat;

public class TestPublisherListener implements IHeartbeatPublisherListener {
	private HeartbeatPublisher publisher = null;
	int hb = 0;

	public TestPublisherListener(HeartbeatPublisher pub) {
		publisher = pub;
	}

	public void notifyHeartbeatPublisherStopped() {
		print("Publisher Stopped");
	}

	public void notifyHeartbeatPublisherStarted() {
		hb = 0;
		print("Publisher Started");
	}

	public void notifyHeartbeatPublisherHeartbeatSent() {
		hb++;
		if (hb > 1) {
			publisher.stopHeartbeatProcess();
		}
		print("Publisher Sent Heartbeat");
	}

	public void notifyHeartbeatPublisherException(Exception erx, HeartbeatBase heartbeat) {
		print("Publisher Exception:" + erx);
	}

	public void print(String message) {
		System.out.println("[" + new java.util.Date().toString() + "] " + message);
	}
}
