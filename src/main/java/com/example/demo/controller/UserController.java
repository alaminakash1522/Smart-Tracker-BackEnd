package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.services.JwtService;
import com.example.demo.services.MyUserDetailsService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        return myUserDetailsService.CreateUser(user);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
            String token = jwtService.generateToken(user.getUsername());

            // Fetch user from DB to get userId
            User dbUser = userRepository.getUserByUsername(user.getUsername());
            return ResponseEntity.ok(
                Map.of(
                        "message", "Login successful",
                        "token", token,
                        "userId", dbUser.getId(),
                        "username", dbUser.getUsername()
                )
        );
        }
        else
            return ResponseEntity.status(401).body("Login Failed");
    }

    @PostMapping("/text")
    public String text(@RequestBody User user) {
        return user.getUsername();
    }
}

