package com.cryptotracker.portfolio.Exception;

public class QuantityZeroBelow extends RuntimeException {
    public QuantityZeroBelow(String message) {
        super(message);
    }
}
