package com.example.library;

import com.example.library.models.ReturnOrderRequest;
import com.example.library.models.ReturnOrderResponse;
import com.example.library.models.SearchRequest;
import com.example.library.models.SearchResponse;
import com.example.library.models.SubmitOrderRequest;
import com.example.library.models.SubmitOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibraryController {
  @Autowired
  LibraryService libraryService;

  @PostMapping(value = "/book")
  public SearchResponse searchBook(@RequestBody SearchRequest request) {
    return libraryService.searchBook(request);
  }

  @PostMapping(value = "/submit-order")
  public SubmitOrderResponse submitOrder(@RequestBody SubmitOrderRequest request) {
    return libraryService.submitOrder(request);
  }

  @PostMapping(value = "/return-order")
  public ReturnOrderResponse returnOrder(@RequestBody ReturnOrderRequest request) {
    return libraryService.returnOrder(request);
  }
}
