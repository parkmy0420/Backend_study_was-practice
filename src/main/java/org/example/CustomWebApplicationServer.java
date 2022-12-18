package org.example;

import org.example.calculator.domain.Calculator;
import org.example.calculator.domain.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CustomWebApplicationServer {
    /**
     * 사용자 요청이 들어올 때 마다 Thread를 생성하여 요청을 처리하도록 한다.
     * ㄴ 메인 스레드에서 처리하게 되면 요청이 들어오고 처리하는동안 블로킹 되어 다음 요청을 받지 못하고 끝날때까지 기다려야함
     */
    private final int port;

    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    public CustomWebApplicationServer(int port) { //생성자
        this.port = port;
    }
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port.", port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServer] waiting for client.");

            while ((clientSocket = serverSocket.accept()) != null) { //클라이언트가 들어올때마다
                logger.info("[CustomWebApplicationServer] client connected!");
                //쓰레드를 생성해서 사용자의 요청을 clientSocket에 전달함
                new Thread((new ClientRequestHandler(clientSocket))).start();
            }
        }
    }
}






