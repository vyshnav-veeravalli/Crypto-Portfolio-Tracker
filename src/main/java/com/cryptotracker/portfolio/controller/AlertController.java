package com.cryptotracker.portfolio.controller;

import com.cryptotracker.portfolio.entity.Alert;
import com.cryptotracker.portfolio.entity.AlertRequest;
import com.cryptotracker.portfolio.service.AlertService;
import com.cryptotracker.portfolio.service.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    SessionManager sessionManager;

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    private String getLoggedInEmail(HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            throw new RuntimeException("User not logged in");
        }
        return email;
    }

    @PostMapping
    public ResponseEntity<Alert> createAlert(@RequestBody AlertRequest request) {
        return ResponseEntity.ok(alertService.createAlert(request));
    }
}
