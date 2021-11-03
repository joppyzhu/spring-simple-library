package com.example.library;

import com.example.library.model.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibraryController {
  @Autowired
  LibraryService libraryService;

  @PostMapping(value = "/search")
  public List<String> search(@RequestBody SearchRequest request) {
    return libraryService.search(request);
  }
}
