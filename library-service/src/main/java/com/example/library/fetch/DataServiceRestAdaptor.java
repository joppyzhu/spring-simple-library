package com.example.library.fetch;

import com.example.library.models.Book;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

public class DataServiceRestAdaptor {

    private RestTemplate restTemplate;

    private String dataBaseUrl;

    public DataServiceRestAdaptor(String dataBaseUrl) {
        this.restTemplate = new RestTemplate();
        this.dataBaseUrl = dataBaseUrl;
    }

    public Book getBookById(Integer bookId) {
        System.out.println("Starting BLOCKING Controller!");
        String dataBaseUrl = "http://localhost:8080/";
        ResponseEntity<Book> response = restTemplate.exchange(
                this.dataBaseUrl + "book/" + bookId,
                HttpMethod.GET,
                null,
                Book.class);
        System.out.println(response.getBody().toString());
        System.out.println("Exiting BLOCKING Controller!");
        return response.getBody();
    }
}
