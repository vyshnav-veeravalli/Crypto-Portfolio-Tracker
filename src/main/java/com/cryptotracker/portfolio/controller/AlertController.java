package com.cryptotracker.portfolio.controller;

import com.cryptotracker.portfolio.entity.Alert;
import com.cryptotracker.portfolio.entity.AlertRequest;
import com.cryptotracker.portfolio.entity.TriggeredAlert;
import com.cryptotracker.portfolio.repository.TriggeredAlertRepository;
import com.cryptotracker.portfolio.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private TriggeredAlertRepository triggeredAlertRepository;

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping("/create")
    public ResponseEntity<Alert> createAlert(@RequestBody AlertRequest request) {
        return ResponseEntity.ok(alertService.createAlert(request));
    }

    @GetMapping("/my")
    public ResponseEntity<List<Alert>> getUserAlerts(@RequestParam String email){
        List<Alert> alerts = alertService.getAlertsByUser(email);
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/triggered")
    public ResponseEntity<List<TriggeredAlert>> getTriggeredAlerts(@RequestParam(required = false) String email){
        if (email != null && !email.trim().isEmpty()) {
            return ResponseEntity.ok(triggeredAlertRepository.findAllByEmail(email));
        } else {
            return ResponseEntity.ok(triggeredAlertRepository.findAll());
        }
    }
}
