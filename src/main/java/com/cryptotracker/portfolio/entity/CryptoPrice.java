package com.cryptotracker.portfolio.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class CryptoPrice {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id int id;

    private String symbol;
    private double currentPrice;
    private LocalDateTime timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

