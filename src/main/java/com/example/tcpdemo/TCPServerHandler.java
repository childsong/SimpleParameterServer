package com.example.tcpdemo;


import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.buffer.IoBufferLazyInitializer;
import org.apache.mina.filter.codec.serialization.ObjectSerializationEncoder;
import org.apache.mina.filter.codec.serialization.ObjectSerializationInputStream;
import org.apache.mina.filter.codec.serialization.ObjectSerializationOutputStream;

import java.awt.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class TCPServerHandler  extends IoHandlerAdapter {
    private CharsetEncoder charsetEncoder = Charset.forName("UTF-8").newEncoder();
    private CharsetDecoder charsetDecoder = Charset.forName("UTF-8").newDecoder();
    private ObjectSerializationInputStream objectSerializationInputStream=null;
    private LinkedHashMap<Integer, Double> getGradient=new LinkedHashMap<Integer,Double>();
    private ObjectSerializationOutputStream objectSerializationOutputStream=null;
    private LinkedHashMap<Integer, Double> sentGradient=new LinkedHashMap<Integer,Double>();
    
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        System.out.println("链接已建立！");


    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        System.out.println("链接已关闭！");

    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        System.out.println("链接处于空闲！");

    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        cause.printStackTrace();
        //session.closeNow();
    }



    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);

       //得到远程机器的address
        ArrayList<String> remoteAddress = getHostname(session);
        System.out.println("Get data from:"+remoteAddress);
//        IoBuffer ioBuffer=(IoBuffer) message;
//        ioBuffer.flip();
//        objectSerializationInputStream=new ObjectSerializationInputStream(ioBuffer.asInputStream());
//        getGradient=(LinkedHashMap<Integer, Double>) objectSerializationInputStream.readObject();
//        System.out.println(objectSerializationInputStream.readObject());  // 需要将object序列化才能传！！！刚好有这个包
//        System.out.println(message);
        LinkedHashMap<Integer, Double> gradientReceive= (LinkedHashMap<Integer, Double>) message;
        LinkedHashMap<Integer, Double> gradientSent= new LinkedHashMap<Integer,Double>();
        for(int i=300;i<600;i++) {
        	gradientSent.put(i, 0.3);
        }
        System.out.println(gradientReceive);
        if(gradientReceive.get(-1)!=null) {
        	session.write(gradientSent);
        }
        else {
        	System.out.println("Perform program");
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);

        System.out.println("Serve端响应客户端发送数据:");

        
    }

    @Override
    public void inputClosed(IoSession session) throws Exception {
        super.inputClosed(session);
    }
    
    public static ArrayList<String> getHostname(IoSession session)
    {
    	ArrayList<String> al_address=new ArrayList<String>();
    	String[] a = session.getRemoteAddress().toString().split(":");
    
    	al_address.add(a[0]);
    	al_address.add(a[1]);
    	return al_address;
        
    }
}