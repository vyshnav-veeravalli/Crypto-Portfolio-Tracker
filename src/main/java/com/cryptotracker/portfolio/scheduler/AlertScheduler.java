package com.cryptotracker.portfolio.scheduler;

import com.cryptotracker.portfolio.entity.Alert;
import com.cryptotracker.portfolio.repository.AlertRepository;
import com.cryptotracker.portfolio.service.AlertService;
import com.cryptotracker.portfolio.service.CryptoPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AlertScheduler {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private CryptoPriceService priceService;

    @Autowired
    private AlertService alertService;

    @Scheduled(fixedRate = 50000)
    public void runEveryMinute() {
        List<Alert> alerts = alertRepository.findAll();
        Set<String> symbols = new HashSet<>();

        for (Alert alert : alerts) {
            symbols.add(alert.getSymbol().toUpperCase());
        }

        for (String symbol : symbols) {
            double livePrice = priceService.getPrice(symbol);
            alertService.checkAlerts(symbol, livePrice);
        }
    }
}
