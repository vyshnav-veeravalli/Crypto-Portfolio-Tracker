package com.cryptotracker.portfolio.DTO;

public class LivePriceDTO {
    private String coinName;
    private String symbol;
    private double currentPrice;

    public LivePriceDTO(String coinName, String symbol, double currentPrice){
        this.coinName=coinName;
        this.symbol=symbol;
        this.currentPrice=currentPrice;
    }

    public String getCoinName() {
        return coinName;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }
}
