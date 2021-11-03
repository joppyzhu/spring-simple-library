package com.example.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
  private Integer bookId;

  private String title;

  private String author;

  private String year;

  private Integer qty;
}
