package com.example.library;

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

  @PostMapping(value = "/search")
  public SearchResponse search(@RequestBody SearchRequest request) {
    return libraryService.search(request);
  }

  @PostMapping(value = "/submit-order")
  public SubmitOrderResponse submitOrder(@RequestBody SubmitOrderRequest request) {
    return libraryService.submitOrder(request);
  }
}
