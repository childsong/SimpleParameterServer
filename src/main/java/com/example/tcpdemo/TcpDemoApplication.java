package com.example.tcpdemo;

//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TcpDemoApplication {

    public static void main(String[] args) {
//        SpringApplication.run(TcpDemoApplication.class, args);
        TCPServer TcpServer = new TCPServer();
    }
}
