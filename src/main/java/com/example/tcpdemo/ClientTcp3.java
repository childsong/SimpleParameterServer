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






