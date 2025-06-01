package com.cryptotracker.portfolio.service;

import com.cryptotracker.portfolio.entity.Alert;
import com.cryptotracker.portfolio.entity.AlertRequest;
import com.cryptotracker.portfolio.entity.TriggeredAlert;
import com.cryptotracker.portfolio.repository.AlertRepository;
import com.cryptotracker.portfolio.repository.TriggeredAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    @Autowired
    private TriggeredAlertRepository triggeredAlert;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private EmailService emailService;


    public Alert createAlert(   AlertRequest request) {
        Alert alert = new Alert();
        alert.setUserEmail(request.getUserEmail());
        alert.setSymbol(request.getSymbol().toUpperCase());
        alert.setTargetPrice(request.getTargetPrice());
        alert.setDirection(request.getDirection().toLowerCase());

        return alertRepository.save(alert);
    }


    public void checkAlerts(String symbol, double price) {
        List<Alert> alerts = alertRepository.findBySymbolIgnoreCase(symbol);


        for (Alert alert : alerts) {
            boolean isAboveTrigger = alert.getDirection().equals("above") && price > alert.getTargetPrice();
            boolean isBelowTrigger = alert.getDirection().equals("below") && price < alert.getTargetPrice();

            if (isAboveTrigger || isBelowTrigger) {

                alert.setTriggeredTime(LocalDateTime.now());
                emailService.sendAlertEmail(alert);

                TriggeredAlert triggered = new TriggeredAlert();
                triggered.setEmail(alert.getUserEmail());
                triggered.setSymbol(alert.getSymbol());
                triggered.setTargetPrice(alert.getTargetPrice());
                triggered.setDirection(alert.getDirection());
                triggered.setTriggeredTime(LocalDate.from(LocalDateTime.now()));

                triggeredAlert.save(triggered);

                alertRepository.delete(alert);
            }
        }
    }

    public List<Alert> getAlertsByUser(String email) {
        return alertRepository.findByUserEmail(email);
    }


}
