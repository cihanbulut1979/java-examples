package com.java.node.hearbeat.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {

	private int counter = 0;

	private String clientId;

	public HeartBeatClientHandler(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("：" + new Date());
		
		SocketAddress serverAddress = ctx.channel().remoteAddress();
		
		InetSocketAddress inetSocketAddress = ((InetSocketAddress) serverAddress);
		
		System.out.println("HeartBeatClientHandler channel active : "  + inetSocketAddress.toString());
		ctx.fireChannelActive();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("：" + new Date());
		
		SocketAddress serverAddress = ctx.channel().remoteAddress();
		
		InetSocketAddress inetSocketAddress = ((InetSocketAddress) serverAddress);
		
		System.out.println("HeartBeatClientHandler channel inactive : " + inetSocketAddress.toString());
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		System.out.println("：" + new Date());
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.WRITER_IDLE) {
				
				System.out.println("client counter : " + counter);
				
				counter++;

				long time = System.currentTimeMillis();

				ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(
						Unpooled.copiedBuffer("Heartbeat:" + clientId + ":" + time, CharsetUtil.UTF_8));

				ctx.channel().writeAndFlush(HEARTBEAT_SEQUENCE.duplicate());
				
			}
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		SocketAddress serverAddress = ctx.channel().remoteAddress();
		
		InetSocketAddress inetSocketAddress = ((InetSocketAddress) serverAddress);
		
		String message = (String) msg;
		System.out.println(message);
		if (message.startsWith("Heartbeat")) {
			ctx.write("has read message from server " + message + " from : " + inetSocketAddress.toString());
			ctx.flush();
		}
		ReferenceCountUtil.release(msg);
	}
}