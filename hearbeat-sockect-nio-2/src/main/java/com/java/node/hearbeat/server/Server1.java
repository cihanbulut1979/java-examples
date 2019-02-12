package com.java.node.hearbeat.server;

public class Server1 {
	public static void main(String[] args) throws Exception {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8080;
		}
		new HeartBeatServer(port).start();
	}
}
