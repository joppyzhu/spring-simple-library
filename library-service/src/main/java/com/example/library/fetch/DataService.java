package com.example.library.fetch;

import com.example.library.models.Book;
import com.example.library.models.Order;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface DataService {
  @GET("/book")
  public Call<List<Book>> getAllBook();

  @POST("/book/search")
  public Call<List<Book>> searchBook(@Body Book book);

  @GET("/book/{id}")
  public Call<Book> getBookById(@Path("id") Integer bookId);

  @POST("/book/update")
  public Call<Book> updateBook(@Body Book book);

  @POST("/order/create")
  public Call<Order> createOrder(@Body Order order);

}
