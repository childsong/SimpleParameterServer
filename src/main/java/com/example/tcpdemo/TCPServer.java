package com.example.tcpdemo;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;


@Controller
public class TCPServer {
    private int port;
    private int readBufferSize;
    private int idleTime;  //空闲时间
    private Charset charset;

    public TCPServer(int port, int readBufferSize, int idleTime, Charset charset) {
        this.port = port;
        this.readBufferSize = readBufferSize;
        this.idleTime = idleTime;
        this.charset = charset;
        init();
    }

    public TCPServer(int port) {
        this.port = port;
        this.readBufferSize = 2048; 
        this.idleTime = 10;
        this.charset = Charset.forName("UTF-8");
        init();
    }
    public TCPServer() {
        this.port = 9999;
        this.readBufferSize = 2048*10;   //只赋值一次，重新编译才有效
        this.idleTime = 10;
        this.charset = Charset.forName("UTF-8");
        init();
    }

    public void init() {
        //初始化TCP/IP基于NIO的套接字
        IoAcceptor acceptor=new NioSocketAcceptor();
        //配置socket会话，
        SocketSessionConfig socketSessionConfig = (SocketSessionConfig) acceptor.getSessionConfig();
        socketSessionConfig.setReadBufferSize(readBufferSize);//设置读取数据缓冲区大小
        socketSessionConfig.setIdleTime(IdleStatus.BOTH_IDLE,idleTime);//设置读写通道在10秒内无任何操作就进入空闲状态
        //配置过滤器
      /*  DefaultIoFilterChainBuilder defaultIoFilterChainBuilder = acceptor.getFilterChain();
        LoggingFilter loggingFilter = new LoggingFilter();
        defaultIoFilterChainBuilder.addLast("loggingFilter", loggingFilter);
        TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory(charset, LineDelimiter.WINDOWS.getValue(),LineDelimiter.WINDOWS.getValue());
        ProtocolCodecFilter protocolCodecFilter = new ProtocolCodecFilter(textLineCodecFactory);
        defaultIoFilterChainBuilder.addLast("protocolCodecFilter",protocolCodecFilter);*/
        
        ProtocolCodecFilter coderFilter=new ProtocolCodecFilter(new ObjectSerializationCodecFactory());
        acceptor.getFilterChain().addLast("serialization", coderFilter);

        //配置NioSocketAcceptor处理器  配置业务逻辑处理器TCPServerHandler，包括接收发送数据的地址和端口
        TCPServerHandler simpleServerHandler = new TCPServerHandler();
        acceptor.setHandler(simpleServerHandler);//将IoHandler
        InetSocketAddress inetSocketAddress = new InetSocketAddress("202.199.6.30",port);
     
        try {
            acceptor.bind(inetSocketAddress);
            System.out.println("服务端启动...");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("服务端异常:" + e.getMessage());
        }


    }
}

