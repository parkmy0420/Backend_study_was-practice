package org.example;

import org.example.calculator.domain.Calculator;
import org.example.calculator.domain.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomWebApplicationServer {
    /**
     * Step1 - 사용자 요청을 메인 Thread가 처리하도록 한다.
     * ㄴ 문제점: 메인 스레드에서 처리하게 되면 요청이 들어와서 해결하는 동안 메인 스레드가 블로킹 되어 다음 요청을 받지 못하고 끝날때까지 대기상태가 된다.
     * ㄴ 해결방법: Step2
     * Step2 - 사용자 요청이 들어올 때마다 Thread를 새로 생성해서 사용자 요청을 처리
     * 하도록 한다.
     * ㄴ 문제점: 요청이 들어올때마다 새로운 Thread가 생성되면 생성될때마다 메모리를 할당 받는다.(비용 증가) -> 성능 저하 -> 요청이 굉장히 많아지면 서버 다운 될 수 있음
     * ㄴ 해결방법: Step3 ( 요청이 들어올때마다 생성하지 않고 고정된 갯수의 Thread를 생성한 후 이를 재활용하는 Thread Pool 형태로 제작)
     * Step3 - Thread Pool을 적용해 안정적인 서비스가 가능하도록 한다.
     */
    private final int port;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

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
                /**
                 * Step3 - Thread Pool을 적용해 안정적인 서비스가 가능하도록 한다.
                 */
                executorService.execute(new ClientRequestHandler(clientSocket));
            }
        }
    }
}






