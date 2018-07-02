package com.example.tcpdemo;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import java.util.LinkedHashMap;


public class ClientHandler extends IoHandlerAdapter{
	private LinkedHashMap<Integer, Double> gradientPull=null;
	
	
	
	 @Override
	    public void sessionCreated(IoSession session) throws Exception {
	        //session 创建时调用
	        System.out.println("session build success");
	    }

	    @Override
	    public void messageReceived(IoSession session, Object message)
	            throws Exception {
	        //异步接收消息
	    	gradientPull = (LinkedHashMap<Integer, Double>) message;
	    	System.out.println("receive message from server");
	    	System.out.println(gradientPull);
	    	
	        session.close(true);
	    }

	    @Override
	    public void exceptionCaught(IoSession session, Throwable cause)
	            throws Exception {
	        //出现异常
	        cause.printStackTrace();
//	        session.close(true);
	    }

	    @Override
	    public void sessionIdle(IoSession session, IdleStatus status)
	            throws Exception {
	        //心跳
	        System.out.println("客户端ide:");
	    }
	    
	    public LinkedHashMap<Integer, Double> getGradientPull(){
	    	return this.gradientPull;
	    }
	  

}
