package com.example.auth.controller;

import com.example.auth.entity.User;
import com.example.auth.service.AuthService;
import com.example.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        authService.register(user);
        return "User registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User u = authService.login(user.getEmail(), user.getPassword());
        return jwtUtil.generateToken(u.getEmail());
    }

    @GetMapping("/profile")
    public String profile(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        if (jwtUtil.validateToken(token)) {
            return "Welcome " + jwtUtil.extractEmail(token);
        }
        return "Invalid token";
    }
}
