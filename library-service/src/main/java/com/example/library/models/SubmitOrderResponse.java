package com.example.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitOrderResponse {
  private String status; // FAILED or SUCCESS

  private Integer orderId;

  private String message;
}
