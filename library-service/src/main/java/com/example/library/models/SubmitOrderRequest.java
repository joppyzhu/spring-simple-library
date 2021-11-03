package com.example.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubmitOrderRequest {
  private Integer bookId;

  private String username;

  private String userphone;
}
