package com.example.library.fetch;

import com.example.library.models.Book;
import com.example.library.models.Order;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@NoArgsConstructor
public class DataServiceAdaptor {

    private WebClient dataClient;

    public DataServiceAdaptor(WebClient dataClient) {
        this.dataClient = dataClient;
    }

    public Mono<Book> getBookById(Integer bookId) {
        System.out.println("Starting NON-BLOCKING Controller!");
        Mono<Book> response = dataClient
                .get()
                .uri("book/" + bookId)
                .retrieve()
                .bodyToMono(Book.class)
                .cache(Duration.ofMinutes(1));
        response.subscribe(order -> {
            System.out.println(order.toString());
        });
        System.out.println("Exiting NON-BLOCKING Controller!");
        return response;
    }
}
