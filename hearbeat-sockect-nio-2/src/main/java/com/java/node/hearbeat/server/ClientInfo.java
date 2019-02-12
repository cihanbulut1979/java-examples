package com.java.node.hearbeat.server;

public class ClientInfo {
	private String clientId;
	private String ip;
	private int port;
	private int loss_connect_time = 0;

	public ClientInfo(String clientId, String ip, int port) {
		this.clientId = clientId;
		this.ip = ip;
		this.port = port;
	}

	public String getClientId() {
		return clientId;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public int getLoss_connect_time() {
		return loss_connect_time;
	}

	public void resetConnectionLoss() {
		loss_connect_time = 0;
	}

	public boolean isConnectionLoss() {
		return loss_connect_time > 0;
	}

	public void increaseConnectionLoss() {
		loss_connect_time++;
	}

}
