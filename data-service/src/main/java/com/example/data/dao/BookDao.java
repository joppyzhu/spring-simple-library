package com.example.data.dao;

import com.example.data.models.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookDao extends CrudRepository<Book, Integer> {
  public List<Book> findAll();

  public List<Book> findByTitleContaining(String title);

  @Query(value = "SELECT * FROM simplelibrary.book WHERE book_id = :id", nativeQuery = true)
  public Book findOne(@Param("id") Integer id);

}
