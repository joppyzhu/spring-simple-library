package com.example.library.models;

import lombok.Data;

@Data
public class ReturnOrderResponse {
  private String status; // FAILED / SUCCESS

  private String message;
}
