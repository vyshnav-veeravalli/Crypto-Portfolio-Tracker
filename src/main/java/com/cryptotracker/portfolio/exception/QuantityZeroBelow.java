package com.cryptotracker.portfolio.exception;

public class QuantityZeroBelow extends RuntimeException {
    public QuantityZeroBelow(String message) {
        super(message);
    }
}
