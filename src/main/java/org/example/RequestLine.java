package org.example;

import java.util.Objects;

/**
 * HttpRequest
 *  - RequestLine   "GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1"
 *      ㄴ HttpMethod
 *      ㄴ path
 *      ㄴ queryString
 *  - Header
 *  -Body
 */
public class RequestLine{
    private final Object method;    //GET
    private final Object urlPath;   // /calculate
    private QueryStrings queryStrings;     // operand1=11&operator=*&operand2=55

    public  RequestLine(String requestLine) {

        // "GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1"
        String[] tokens = requestLine.split(" ");
        this.method = tokens[0];
        String[] urlPathTokens = tokens[1].split("\\?");
        this.urlPath = urlPathTokens[0];

        if(urlPathTokens.length == 2){
            this.queryStrings = new QueryStrings(urlPathTokens[1]);
        }
    }

    public RequestLine(String method, String urlPath, String queryStrings){
        this.method = method;
        this.urlPath = urlPath;
        this.queryStrings = new QueryStrings(queryStrings);
    }


    public boolean isGetRequest() {
        return "GET".equals(this.method);
    }

    public boolean matchPath(String requestPath) {
        return urlPath.equals(requestPath);
    }

    public QueryStrings getQueryStrings() {
        return this.queryStrings;
    }

    // 객체와 객체를 비교할때는 꼭 필요함 equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) && Objects.equals(urlPath, that.urlPath) && Objects.equals(queryStrings, that.queryStrings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, urlPath, queryStrings);
    }

}
