package com.example.data.services;

import com.example.data.Util;
import com.example.data.dao.BookDao;
import com.example.data.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ComponentScan({"com.example.data"})
public class BookService {
  @Autowired
  BookDao bookDao;

  public List<Book> findAll() {
    List<Book> bookList = bookDao.findAll();
    return bookList;
  }

  public long count() {
    long countItem = bookDao.count();
    return countItem;
  }

  public List<Book> findByTitle(String title) {
    List<Book> bookList = bookDao.findByTitleContaining(title);
    if (bookList.isEmpty()) {
      return null;
    } else {
      return bookList;
    }
  }

  public Book findById(Integer id) {
    try {
      Thread.sleep(10);
    } catch (Exception e) {

    }
    Book customer = bookDao.findOne(id);
    return customer;
  }

  public Book create(Book book) {
    return bookDao.save(book);
  }

  public Book update(Book book) {
    Book bookTemp = bookDao.findOne(book.getBookId());
    Util.copyNonNullProperties(book, bookTemp);
    return bookDao.save(bookTemp);
  }

  public void delete(Integer id) {
    bookDao.deleteById(id);
  }


}
