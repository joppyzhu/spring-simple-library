package com.example.library;

import com.example.library.fetch.DataService;
import com.example.library.models.Book;
import com.example.library.models.SearchRequest;
import com.example.library.models.SearchResponse;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {
  private DataService dataService;

  public LibraryService() {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://localhost:8080/")
        .addConverterFactory(GsonConverterFactory.create())
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
}
