package com.example.library.fetch;

import com.example.library.models.Book;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeasy.random.EasyRandom;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class DataServiceRestAdaptor {

    private RestTemplate restTemplate;

    private String dataBaseUrl;

    public DataServiceRestAdaptor(String dataBaseUrl) {
        this.restTemplate = new RestTemplate();
        this.dataBaseUrl = dataBaseUrl;
    }

    public Book getBookById(HttpHeaders httpHeaders, Integer bookId, String requestId) {
        // Simple RestTemplate
        ResponseEntity<Book> response = restTemplate.exchange(
                this.dataBaseUrl + "book/" + bookId + "/" + requestId,
                HttpMethod.GET,
                null,
                Book.class);
        //System.out.println(response.getBody().toString());
        //System.out.println("Exiting BLOCKING Controller!");
        return response.getBody();
        // End Simple RestTemplate

        // RestTemplate with interceptors
        /*EasyRandom generator = new EasyRandom();
        ApiContext apiContext = generator.nextObject(ApiContext.class);
        apiContext.setHttpHeaders(httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(this.dataBaseUrl + "book/" + bookId)
                .queryParam("parameterKey", "Oke")
                .build();
        RestTemplate restTemplate = new RestTemplateBuilder()
                .interceptors(List.of(new HttpHeadersInterceptor(apiContext)))
                .setConnectTimeout(Duration.ofMillis(5000))
                .setReadTimeout(Duration.ofMillis(5000))
                .build();
        Book response2 = restTemplate.getForEntity(
                uriComponents.toUriString(),
                Book.class).getBody();
        System.out.println(response2.toString());
        return response2;*/
        // End RestTemplate with interceptors
    }

    // From core-helper
    public class HttpHeadersInterceptor implements ClientHttpRequestInterceptor {
        private ApiContext apiContext;

        public HttpHeadersInterceptor(ApiContext apiContext) {
            this.apiContext = apiContext;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            apiContext.getHttpHeaders().entrySet().stream()
                    .filter(x -> x.getKey() != null)
                    .forEach(x -> {
                        System.out.println(x.getKey() + " = " + x.getValue().get(0));
                        request.getHeaders().add(x.getKey(), x.getValue().get(0));
                    });
            return execution.execute(request, body);
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ApiContext {

        private HttpHeaders httpHeaders;
        private String userRefId;
        private String customerId;
        private String otpMobileNo;
        private String cid;
        private String passportNo;
        private String tokenScope;
        private String deviceId;
        private String boUserFullName;
        private String boUserId;
        private String authorization;
        private String language;
        private String requestId;
        private String correlationId;
        private String forwardedFor;
        private String userAgent;
        private String platform;
        private String clientVersion;
        private String channelId;
        private String apiKey;
        private String sleuthId;
        private String userName;
        private String userId;
        private String smUniversalId;
    }
}
