package com.example.tcpdemo;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.boot.web.servlet.server.Session;

import javax.tools.Tool;
import java.io.*;
import java.net.InetSocketAddress;
import org.apache.mina.filter.codec.serialization.ObjectSerializationOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.example.tcpdemo.GradientCommunication;

public class ClientTcp3 {

       

//
//        public void receiveAndSend() throws IOException {
//
//            InputStream input = null;
//
//            OutputStream output = null;
//
//
//
//
//
//            try {
//
//                if (socket == null ||socket.isClosed() || !socket.isConnected()) {
//
//                    socket = new Socket();
//
//                    InetSocketAddress addr = new InetSocketAddress(ip, port);
//                    
//                    
//                    socket.connect(addr, timeout);
//                    
//                
//                    socket.setSoTimeout(timeout);  //启用带有超时的socket
//                    
//                    
//                    System.out.println(socket.getLocalAddress());
//                    
//                    System.out.println("TcpKeepAliveClientnew ");
//                    
//                   
//                    
//
////                    OutputStream os = socket.getOutputStream();
////                    String message = "hello I'am TcpKeepAliveClientnew";
////                    
////                    float[] a=new float[10000];
////                    for(int i=0;i<a.length;i++) {
////                    	a[i]=(float) 0.8;
////                    }
//                    
////                    System.out.println("TcpClient打招呼:" +message);
//                    
////                    os. write(message.getBytes());
//                    
//                    //发送Hashmap类型的数据
//                   
//                    
//                    
//                    
//                    ObjectSerializationOutputStream objectSerializationOutputStream=new ObjectSerializationOutputStream(socket.getOutputStream());
//                    objectSerializationOutputStream.writeObject(hashMap);
//                    
//
//                    
//                    hashMap.clear();
//                    for(int i=300;i<600;i++) {
//                    	hashMap.put(i,2.0);
//                    }
//                    objectSerializationOutputStream.writeObject(hashMap);
//                    
//                    
////                    hashMap.clear();
////                    for(int i=600;i<900;i++) {
////                    	hashMap.put(i,2.0);
////                    }
////                    objectSerializationOutputStream.writeObject(hashMap);
////                    
////                    hashMap.clear();
////                    for(int i=900;i<1200;i++) {
////                    	hashMap.put(i,2.0);
////                    }
////                    objectSerializationOutputStream.writeObject(hashMap);
////
////                    hashMap.clear();
////                    for(int i=1200;i<1500;i++) {
////                    	hashMap.put(i,2.0);
////                    }
////                    objectSerializationOutputStream.writeObject(hashMap);
//
//                }
//
//
//
//                input = socket.getInputStream();
//
//                output = socket.getOutputStream();
//
//
//
//                // read body
//
//                byte[] receiveBytes = {};// 收到的包字节数组
//
//                while (true) {
//
//                    if (input.available() > 0) {
//
//                        receiveBytes = new byte[input.available()];
//
//                        input.read(receiveBytes);
//
//
//
//                        // send
//
//                        System.out.println("TcpKeepAliveClientsend date :" +new String(receiveBytes));
//
//                        output.write(receiveBytes, 0, receiveBytes.length);
//
//                        output.flush();
//
//                    }
//
//                   /* if(number != 0){
//                        continue;
//                    } else {
//
//                        OutputStream os = socket.getOutputStream();
//                        String message = "Bye bye";
//                        os. write(message.getBytes());
//                        socket.close();//释放
//                        System.out.println("【ClientTcp3】TCP客户端断开链接！");
//                        break;
//                    }*/
//
//
//                }
//
//
//
//            } catch (Exception e) {
//
//                e.printStackTrace();
//
//                System.out.println("TcpClientnew socket error");
//
//            }
//
//        }



        public static void main(String[] args) throws Exception {
        	
        	LinkedHashMap<Integer,Double> hashMap=new LinkedHashMap<Integer,Double>();
            for(int i=0;i<300;i++) {
            	hashMap.put(i,2.0);
            }

            GradientCommunication gradientCommunication = new GradientCommunication("202.199.6.30", 9999);
            gradientCommunication.setGradient(hashMap);
            gradientCommunication.buildLongConnection();
            gradientCommunication.push();
            Thread.sleep(3000);
            gradientCommunication.pull();
            

        }






}






