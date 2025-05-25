package com.example.currency_exchange.controller;

import com.example.currency_exchange.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        // For demo: accept any username/password
        String token = JwtUtil.generateToken(username);
        return ResponseEntity.ok().body(java.util.Collections.singletonMap("token", token));
    }
}