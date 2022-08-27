package com.nitinrane.learning.spring;

import lombok.Data;

@Data
public class QuoteResponse {
  private Quote value;
  private String status;
}