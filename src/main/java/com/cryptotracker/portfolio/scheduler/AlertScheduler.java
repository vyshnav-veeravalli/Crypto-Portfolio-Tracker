package com.cryptotracker.portfolio.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AlertScheduler {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private CryptoPriceService priceService;

    @Autowired
    private AlertService alertService;

    @Scheduled(fixedRate = 50000) // Run every 50 seconds
    public void runEveryMinute() {
        List<Alert> alerts = alertRepository.findAll();
        Set<String> symbols = new HashSet<>();

        for (Alert alert : alerts) {
            symbols.add(alert.getSymbol().toUpperCase());
        }

        for (String symbol : symbols) {
            double livePrice = priceService.getcurrentPriceBySymbol(symbol);
            alertService.checkAlerts(symbol, livePrice);
        }
    }
}
