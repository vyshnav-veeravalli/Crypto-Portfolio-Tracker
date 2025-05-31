package com.cryptotracker.portfolio.service;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SessionManager {

    private Set<String> loggedInUsers = new HashSet<>();

    public void login(String email) {
        loggedInUsers.add(email);
    }

    public void logout(String email) {
        loggedInUsers.remove(email);
    }

    public boolean isLoggedIn(String email) {
        return loggedInUsers.contains(email);
    }
}
