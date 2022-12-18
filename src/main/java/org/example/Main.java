package org.example;


import java.io.IOException;

// GET /calculate?operand1=11&operator=*&operand2=55
// ->get에 calculate의 요청이 들어오면 계산을 수행해서 결과를 return

public class Main {
    public static void main(String[] args) throws IOException {
        new CustomWebApplicationServer(8080).start();
    }
}