package com.example.library;

import com.example.library.fetch.DataService;
import com.example.library.models.Book;
import com.example.library.models.Order;
import com.example.library.models.SearchRequest;
import com.example.library.models.SearchResponse;
import com.example.library.models.SubmitOrderRequest;
import com.example.library.models.SubmitOrderResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LibraryService {
  private DataService dataService;

  public LibraryService() {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Gson gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd")
        .create();
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://localhost:8080/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient.build())
        .build();
    dataService = retrofit.create(DataService.class);
  }

  public SearchResponse search(SearchRequest request) {
    SearchResponse response = new SearchResponse();
    if (request.getTitle() == null || request.getTitle().isEmpty()) {
      // No filter then get all
      Call<List<Book>> callSync = dataService.getAllBook();
      try {
        Response<List<Book>> apiResponse = callSync.execute();
        List<Book> bookList = apiResponse.body();
        response.setBooks(bookList);
        return response;
      } catch (Exception ex) {
        // Logger or do nothing
      }
    } else {
      // Use filter
      Book filterBook = new Book();
      filterBook.setTitle(request.getTitle());
      Call<List<Book>> callSync = dataService.searchBook(filterBook);
      try {
        Response<List<Book>> apiResponse = callSync.execute();
        List<Book> bookList = apiResponse.body();
        response.setBooks(bookList);
        return response;
      } catch (Exception ex) {
        // Logger or do nothing
      }
    }
    return response;
  }

  public SubmitOrderResponse submitOrder(SubmitOrderRequest request) {
    SubmitOrderResponse response = new SubmitOrderResponse();
    response.setStatus("FAILED");
    // 1. Get qty of book
    Call<Book> bookCall = dataService.getBookById(request.getBookId());
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
}
