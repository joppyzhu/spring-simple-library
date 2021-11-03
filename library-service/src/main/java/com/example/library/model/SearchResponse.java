package com.example.library.model;

import lombok.Data;

import java.util.List;

@Data
public class SearchResponse {
  List<String> books;
}
