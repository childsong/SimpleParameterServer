package com.example.tcpdemo;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.mockito.internal.matchers.Null;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.*;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.io.*;
import java.nio.*;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.lang.String;


public class GradientCommunication {
	private LinkedHashMap<Integer,Double> gradientPush=new LinkedHashMap<Integer,Double>();
	private LinkedHashMap<Integer, Double> gradientPull=new LinkedHashMap<Integer,Double>();
	private String ip;
	private int port;
	private static int timeout=50*1000;
	private static IoSession session=null;
	private static NioSocketConnector connector;
	private static ClientHandler clientHandler=null;
	
	
	
	public GradientCommunication(String ip, int port) {
		this.ip=ip;
		this.port=port;
	}
	
	
	public void setGradient(LinkedHashMap<Integer, Double> hashMap) {
		this.gradientPush = hashMap;
	}

	public void buildLongConnection() throws IOException, InterruptedException {
		try {
			connector = new NioSocketConnector();
			connector.setConnectTimeoutMillis(timeout);
			// 设置读缓冲,传输的内容必须小于此缓冲
			connector.getSessionConfig().setReadBufferSize(2048 * 2048);
			// 设置编码解码器
			connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
			// 设置日志过滤器
			// connector.getFilterChain().addLast("logger", new LoggingFilter());
			// 设置Handler
			clientHandler=new ClientHandler();
			connector.setHandler(clientHandler);

			// 获取连接，该方法为异步执行
			ConnectFuture future = connector.connect(new InetSocketAddress(ip, port));
			// 等待连接建立
			future.awaitUninterruptibly();
			// 获取session
			session = future.getSession();

			// 等待session关闭
			// session.getCloseFuture().awaitUninterruptibly();
			// 释放connector

		} catch (Exception e) {

			e.printStackTrace();

			System.out.println("TcpClientnew socket error");

		}
	}

	public void push() throws IOException {
		session.write(gradientPush);
	}
	
	public void pull() throws IOException, Exception{
		LinkedHashMap<Integer, Double> pullFlag=new LinkedHashMap<Integer,Double>();
		pullFlag.put(-1, -1.0);
		session.write(pullFlag);
		gradientPull = clientHandler.getGradientPull();
		System.out.println("++"+gradientPull);
	}
	

	
}
