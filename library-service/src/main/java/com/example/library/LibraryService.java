package com.example.library;

import com.example.library.fetch.DataService;
import com.example.library.fetch.DataServiceAdaptor;
import com.example.library.fetch.DataServiceRestAdaptor;
import com.example.library.models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class LibraryService {

  private DataService dataService; // Using Retrofit
  private DataServiceAdaptor dataServiceAdaptor; // Using WebClient
  private DataServiceRestAdaptor dataServiceRestAdaptor; // Using RestTemplate

  public LibraryService(@Value("${config.data-service.url}") String dataUrl) {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    //HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    //httpClient.addInterceptor(logging);
    Gson gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd")
        .create();
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(dataUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient.build())
        .build();
    dataService = retrofit.create(DataService.class);
    dataServiceAdaptor = new DataServiceAdaptor(WebClient.builder().baseUrl(dataUrl).build());
    dataServiceRestAdaptor = new DataServiceRestAdaptor(dataUrl);
  }

  /*O
  Testing the httpClient based on BookId
   */
  public Book getBookDetail(HttpHeaders httpHeaders, Integer bookId) {
    String requestId = UUID.randomUUID().toString();
    long startTime = System.currentTimeMillis();
    if (bookId == 1) {
      // Use RestTemplate
      System.out.println("[" + requestId + "] RestTemplate Start at  " + startTime);
      Book book = dataServiceRestAdaptor.getBookById(httpHeaders, bookId, requestId);
      long endTime = System.currentTimeMillis();
      long diff = endTime - startTime;
      System.out.println("[" + requestId + "] RestTemplate Finish at " + endTime + " (" + diff + "ms)");
      return book;
    } else if (bookId == 2) {
      // Use WebClient
      System.out.println("[" + requestId + "] WebClient Start at  " + startTime);
      Mono<Book> bookMono = dataServiceAdaptor.getBookById(bookId, requestId);
      long endTime = System.currentTimeMillis();
      long diff = endTime - startTime;
      System.out.println("[" + requestId + "] WebClient Finish at " + endTime + " (" + diff + "ms)");
      return bookMono.block();
    } else {
      // Use Retrofit
      System.out.println("[" + requestId + "] Retrofit Start at  " + startTime);
      Call<Book> bookCall = dataService.getBookById(bookId, requestId);
      long endTime = System.currentTimeMillis();
      long diff = endTime - startTime;
      System.out.println("[" + requestId + "] Retrofit Finish at " + endTime + " (" + diff + "ms)");
      try {
        return bookCall.execute().body();
      } catch (Exception e) {
        return null;
      }
    }
  }

  public SearchResponse searchBook(SearchRequest request) {
    SearchResponse response = new SearchResponse();
    List<Book> bookList = new ArrayList<>();
    if (request.getTitle() == null || request.getTitle().isEmpty()) {
      // No filter then get all
      Call<List<Book>> callSync = dataService.getAllBook();
      try {
        Response<List<Book>> apiResponse = callSync.execute();
        bookList = apiResponse.body();
      } catch (Exception ex) {
        // Put some log
      }
    } else {
      // Use filter
      Book filterBook = new Book();
      filterBook.setTitle(request.getTitle());
      System.out.println("Call dataService.searchBook");
      Call<List<Book>> callSync = dataService.searchBook(filterBook);
      System.out.println("End call dataService.searchBook");

      try {
        Response<List<Book>> apiResponse = callSync.execute();
        bookList = apiResponse.body();
      } catch (Exception ex) {
        // Put some log
      }
    }
    response.setBooks(bookList);
    return response;
  }

  public SearchOrderResponse searchOrder(SearchOrderRequest request) {
    SearchOrderResponse response = new SearchOrderResponse();
    List<Order> orderList = new ArrayList<>();
    if (request.getUsername() == null || request.getUsername().isEmpty()) {
      // No filter then get all
      Call<List<Order>> callSync = dataService.getAllOrder();
      try {
        Response<List<Order>> apiResponse = callSync.execute();
        orderList = apiResponse.body();
      } catch (Exception ex) {
        // Put some log
      }
    } else {
      // Use filter
      Order filter = new Order();
      filter.setUsername(request.getUsername());
      Call<List<Order>> callSync = dataService.searchOrder(filter);
      try {
        Response<List<Order>> apiResponse = callSync.execute();
        orderList = apiResponse.body();
      } catch (Exception ex) {
        // Put some log
      }
    }
    response.setOrders(orderList);
    return response;
  }

  public OrderDetail getOrderDetail(Integer orderId) {
    String requestId = UUID.randomUUID().toString();
    OrderDetail response = new OrderDetail();
    Call<Order> orderCall = dataService.getOrderById(orderId);
    try {
      Order order = orderCall.execute().body();
      response.setOrderId(order.getOrderId());
      response.setBookId(order.getBookId());
      response.setUsername(order.getUsername());
      response.setUserphone(order.getUserphone());
      response.setStartDate(order.getStartDate());
      response.setEndDate(order.getEndDate());
      response.setStatus(order.getStatus());
      Call<Book> bookCall = dataService.getBookById(order.getBookId(), requestId);
      try {
        Book book = bookCall.execute().body();
        response.setBookInfo(book);
      } catch (Exception ex) {
        // Put some log
      }
    } catch (Exception ex) {
      // Put some log
    }
    return response;
  }

  public SubmitOrderResponse submitOrder(SubmitOrderRequest request) {
    String requestId = UUID.randomUUID().toString();
    SubmitOrderResponse response = new SubmitOrderResponse();
    response.setStatus("FAILED");
    // 1. Get qty of book
    Call<Book> bookCall = dataService.getBookById(request.getBookId(), requestId);
    try {
      // 2. If stock available then subtract qty
      Response<Book> apiResponse = bookCall.execute();
      Book book = apiResponse.body();
      if (book.getQty() > 0) {
        book.setQty(book.getQty() - 1);
        Call<Book> updatedBook = dataService.updateBook(book);
        try {
          updatedBook.execute();
          // 3. Create order, start_date = today, end_date = today + 7 day (1 week)
          Order order = new Order();
          order.setBookId(book.getBookId());
          order.setUsername(request.getUsername());
          order.setUserphone(request.getUserphone());
          Date startDate = new Date();
          Date endDate = new Date(startDate.getTime() + (7000 * 60 * 60 * 24));
          order.setStartDate(startDate);
          order.setEndDate(endDate);
          order.setStatus("OPEN");
          Call<Order> orderCall = dataService.createOrder(order);
          try {
            Response<Order> orderResponse = orderCall.execute();
            Order orderNew = orderResponse.body();
            response.setStatus("SUCCESS");
            response.setOrderId(orderNew.getOrderId());
            response.setMessage("Congrats your order id is: " + orderNew.getOrderId() + ". Please show your order id when return the book.");
          } catch (Exception e) {
            response.setMessage("Failed connection");
          }
        } catch (Exception e) {
          response.setMessage("Failed connection");
        }
      } else {
        response.setMessage("Book out of order");
      }
    } catch (Exception e) {
      response.setMessage("Failed connection");
    }
    return response;
  }

  public ReturnOrderResponse returnOrder(ReturnOrderRequest request) {
    String requestId = UUID.randomUUID().toString();
    ReturnOrderResponse response = new ReturnOrderResponse();
    response.setStatus("FAILED");
    // 1. Get order detail
    Call<Order> orderCall = dataService.getOrderById(request.getOrderId());
    try {
      Order order = orderCall.execute().body();
      if (order.getStatus().equalsIgnoreCase("OPEN")) {
        // 2. Get book info
        Call<Book> bookCall = dataService.getBookById(order.getBookId(), requestId);
        try {
          Book book = bookCall.execute().body();
          book.setQty(book.getQty() + 1);
          // 3. Add Qty Book
          Call<Book> updatedBook = dataService.updateBook(book);
          try {
            updatedBook.execute();
            // 4. Closed the order
            order.setStatus("CLOSED");
            Call<Order> updateOrderCall = dataService.updateOrder(order);
            try {
              Order orderResponse = updateOrderCall.execute().body();
              if (orderResponse.getStatus().equalsIgnoreCase("CLOSED")) {
                response.setStatus("SUCCESS");
                response.setMessage("Thank you for returning the book");
              } else {
                response.setMessage("Something Wrong");
              }
            } catch (Exception e) {
              response.setMessage("Failed connection");
            }
          } catch (Exception e) {
            response.setMessage("Failed connection");
          }
        } catch (Exception e) {
          response.setMessage("Failed connection");
        }
      } else {
        response.setMessage("Order already closed");
      }
    } catch (Exception e) {
      response.setMessage("Failed connection");
    }
    return response;
  }

}
