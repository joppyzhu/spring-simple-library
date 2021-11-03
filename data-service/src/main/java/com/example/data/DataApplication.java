package com.example.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.example.data"})
public class DataApplication {
  public static void main(String[] args) {
    SpringApplication.run(DataApplication.class, args);
  }
}
