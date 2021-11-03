package com.example.library.fetch;

import com.example.library.models.Book;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface DataService {
  @GET("/book")
  public Call<List<Book>> getAllBook();

  @POST("/book/search")
  public Call<List<Book>> searchBook(@Body Book book);

}
