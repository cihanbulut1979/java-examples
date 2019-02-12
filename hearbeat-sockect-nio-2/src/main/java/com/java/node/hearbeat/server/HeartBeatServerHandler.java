package com.java.node.hearbeat.server;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {

	private Map<String, ClientInfo> clients = new HashMap<String, ClientInfo>();

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {

				SocketAddress clientAddress = ctx.channel().remoteAddress();

				ClientInfo client = getClient(clientAddress);

				if (client != null) {

					client.increaseConnectionLoss();

					System.out.println("5 saniye içinde cevap gelmedi for client : " + client.getClientId());
					if (client.getLoss_connect_time() > 2) {
						System.out.println("loss_connect_time > 2 for client : " + client.getClientId());
						// ctx.channel().close();

						// failover
						System.out.println("Failover for client : " + client.getClientId());
					}

				} else {
					System.out.println("Client not found for adress : " + clientAddress.toString());
				}
			}
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Server channel read..");

		SocketAddress clientAddress = ctx.channel().remoteAddress();

		InetSocketAddress inetSocketAddress = ((InetSocketAddress) clientAddress);

		String ip = inetSocketAddress.getAddress().getHostAddress();
		int port = inetSocketAddress.getPort();

		System.out.println(clientAddress + " -> Server : " + msg.toString());

		if (msg.toString().startsWith("Heartbeat")) {
			String[] split = msg.toString().split(":");

			String heartbeat = split[0];
			String clientId = split[1];
			String clientTime = split[2];
			String serverTime = System.currentTimeMillis() + "";

			System.out.println("Server received Heartbeat message from client : " + clientId + " client time : "
					+ clientTime + " server time : " + serverTime);

			ClientInfo client = clients.get(clientId);

			if (client == null) {

				client = new ClientInfo(clientId, ip, port);

				clients.put(clientId, client);
			} else {
				if (client.isConnectionLoss()) {
					System.out.println("Server resetting connection loss for Client : " + clientId);
					client.resetConnectionLoss();
				}
			}
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();

	}

	private ClientInfo getClient(SocketAddress address) {
		InetSocketAddress inetSocketAddress = ((InetSocketAddress) address);

		String ip = inetSocketAddress.getAddress().getHostAddress();
		int port = inetSocketAddress.getPort();

		Set<String> clientKeys = clients.keySet();

		for (String clientKey : clientKeys) {
			ClientInfo client = clients.get(clientKey);

			if (ip.equals(client.getIp()) && port == client.getPort()) {
				return client;
			}
		}

		return null;
	}
}
