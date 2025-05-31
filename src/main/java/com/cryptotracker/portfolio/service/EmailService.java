package com.cryptotracker.portfolio.service;

import com.cryptotracker.portfolio.entity.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAlertEmail(Alert alert) {
        String userEmail = alert.getUserEmail();
        if (userEmail == null || userEmail.trim().isEmpty()) {
            System.out.println("❌ Email not sent: userEmail is null or empty for alert ID " + alert.getId());
            return;
        }

        String subject = "🚨 Crypto Alert Triggered!";
        String body = String.format(
                "Your alert for %s has been triggered!\n\nThreshold: ₹%.2f\nDirection: %s\nTime: %s",
                alert.getSymbol(), alert.getTargetPrice(), alert.getDirection(), alert.getTriggeredTime()
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("vyshnavveeravalli2479@gmail.com");

        mailSender.send(message);
    }
}
