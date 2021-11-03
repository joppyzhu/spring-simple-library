package com.example.data.controllers;

import com.example.data.models.Book;
import com.example.data.models.Order;
import com.example.data.services.BookService;
import com.example.data.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataController {
  @Autowired
  private BookService bookService;
  @Autowired
  private OrderService orderService;

  @GetMapping(value = "/book")
  public List<Book> findAll() {
    return bookService.findAll();
  }

  @GetMapping(value = "/book/count")
  public long count() {
    return bookService.count();
  }

  @GetMapping(value = "/book/{id}")
  public Book findById(@PathVariable Integer id) {
    return bookService.findById(id);
  }

  @PostMapping(value = "/book/search")
  public List<Book> search(@RequestBody Book book) {
    return bookService.findByTitle(book.getTitle());
  }

  @PostMapping(value = "/book/create")
  public Book create(@RequestBody Book book) {
    return bookService.create(book);
  }

  @PostMapping(value = "/book/update")
  public Book update(@RequestBody Book book) {
    return bookService.update(book);
  }

  @PostMapping(value = "/book/delete")
  public void delete(@RequestBody Book book) {
    bookService.delete(book.getBookId());
  }

  @GetMapping(value = "/order")
  public List<Order> findAllOrder() {
    return orderService.findAll();
  }

  @GetMapping(value = "/order/count")
  public long orderCount() {
    return orderService.count();
  }

  @GetMapping(value = "/order/{id}")
  public Order findOrderById(@PathVariable Integer id) {
    return orderService.findById(id);
  }

  @PostMapping(value = "/order/search")
  public List<Order> searchOrder(@RequestBody Order order) {
    return orderService.findOrder(order.getUsername());
  }

  @PostMapping(value = "/order/create")
  public Order createOrder(@RequestBody Order order) {
    return orderService.create(order);
  }

  @PostMapping(value = "/order/update")
  public Order updateOrder(@RequestBody Order order) {
    return orderService.update(order);
  }

  @PostMapping(value = "/order/delete")
  public void deleteOrder(@RequestBody Order order) {
    orderService.delete(order.getOrderId());
  }
}
