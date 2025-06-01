package com.cryptotracker.portfolio.Exception;

public class EmailEmptyException extends RuntimeException {
  public EmailEmptyException(String message) {
    super(message);
  }
}
