package com.cryptotracker.portfolio.controller;

import com.cryptotracker.portfolio.DTO.LoginDTO;
import com.cryptotracker.portfolio.DTO.UserDTO;
import com.cryptotracker.portfolio.entity.User;
import com.cryptotracker.portfolio.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class Controller {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDTO userDTO) {
        User savedUser = userService.register(userDTO);
        savedUser.setPassword("Encrypted");
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        String email = loginDTO.getEmail();
        boolean success = userService.login(loginDTO);

        if (success) {
            session.setAttribute("email", email);
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.status(401).body("Login Failed");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout Successful");
    }
}
