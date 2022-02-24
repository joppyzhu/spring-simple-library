package com.example.library;

import com.example.library.models.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ApiOperation(value = "/", tags = "Library API Service")
@RestController
public class LibraryController {
  @Autowired
  LibraryService libraryService;

  @ApiOperation(value = "Get and Search Book")
  @PostMapping(value = "/book")
  public ResponseEntity<SearchResponse> searchBook(@RequestBody SearchRequest request) {
    SearchResponse response =  libraryService.searchBook(request);
    if (response == null || response.getBooks().isEmpty()) {
      response.setErrorCode("404");
      response.setErrorMessage("Search not found");
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(response, HttpStatus.OK);
    }
  }

  @ApiOperation(value = "Get and Search Order")
  @PostMapping(value = "/order")
  public SearchOrderResponse searchOrder(@RequestBody SearchOrderRequest request) {
    return libraryService.searchOrder(request);
  }

  @ApiOperation(value = "Get Book Detail")
  @GetMapping(value = "/book/{bookId}")
  public Book bookDetail(@PathVariable Integer bookId) {
    return libraryService.getBookDetail(bookId);
  }

  @ApiOperation(value = "Get Order Detail")
  @GetMapping(value = "/order/{orderId}")
  public OrderDetail orderDetail(@PathVariable Integer orderId) {
    return libraryService.getOrderDetail(orderId);
  }

  @ApiOperation(value = "Submit Order")
  @PostMapping(value = "/submit-order")
  public SubmitOrderResponse submitOrder(@RequestBody SubmitOrderRequest request) {
    return libraryService.submitOrder(request);
  }

  @ApiOperation(value = "Return Order")
  @PostMapping(value = "/return-order")
  public ReturnOrderResponse returnOrder(@RequestBody ReturnOrderRequest request) {
    return libraryService.returnOrder(request);
  }
}
