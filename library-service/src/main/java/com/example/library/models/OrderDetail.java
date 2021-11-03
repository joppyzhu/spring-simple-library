package com.example.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
  private Integer orderId;

  private Integer bookId;

  private String username;

  private String userphone;

  private Date startDate;

  private Date endDate;

  private String status;

  private Book bookInfo;
  
}
