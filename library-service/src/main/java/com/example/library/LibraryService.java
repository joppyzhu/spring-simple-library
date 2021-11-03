package com.example.library;

import com.example.library.model.SearchRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {

  public List<String> search(SearchRequest request) {
    if (request.getValue() == null || request.getValue().isEmpty()) {
      // No filter then get all
    } else {
      // Use filter
    }
    List<String> response = new ArrayList<>();
    response.add("OKe");
    return response;
  }
}
