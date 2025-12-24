package com.example.bankapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bankapp.dto.LoginRequest;
import com.example.bankapp.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        if ("user".equals(request.getUsername()) &&
            "password".equals(request.getPassword())) {

            String token = jwtUtil.generateToken(
                    request.getUsername(),
                    "USER"   // ✅ ONLY USER
            );

            return ResponseEntity.ok(Map.of("token", token));
        }

        if ("admin".equals(request.getUsername()) &&
            "adminpass".equals(request.getPassword())) {

            String token = jwtUtil.generateToken(
                    request.getUsername(),
                    "ADMIN"  // ✅ ONLY ADMIN
            );

            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid credentials"));
    }
}