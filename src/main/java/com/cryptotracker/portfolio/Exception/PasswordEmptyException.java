package com.cryptotracker.portfolio.Exception;

public class PasswordEmptyException extends RuntimeException {
  public PasswordEmptyException(String message) {
    super(message);
  }
}
