package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * HttpRequest
 *  - RequestLine   "GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1"
 *      ㄴ HttpMethod
 *      ㄴ path
 *      ㄴ queryString
 *  - Header
 *  -Body
 */

public class RequestLineTest {
    @Test
    void create() {
        RequestLine requestLine = new RequestLine("GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1");

        assertThat(requestLine).isNotNull();
        assertThat(requestLine).isEqualTo(new RequestLine("GET", "/calculate", "operand1=11&operator=*&operand2=55"));
    }
}
