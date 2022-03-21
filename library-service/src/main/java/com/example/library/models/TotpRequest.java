package com.example.library.models;

import lombok.Data;

@Data
public class TotpRequest {
  private String secretKey;
  private String code;

}
