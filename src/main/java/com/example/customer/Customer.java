package com.example.customer;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Customer {
  @Id
  private String id;
  private String name;
  private String email;

  // getters and setters
}