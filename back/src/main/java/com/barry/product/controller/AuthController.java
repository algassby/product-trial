package com.barry.product.controller;

import com.barry.product.dto.request.LoginRequest;
import com.barry.product.dto.request.UserRequest;
import com.barry.product.dto.response.UserResponse;
import com.barry.product.service.AuthService;
import com.barry.product.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/account")
    public ResponseEntity<?> createAccount(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(token);
    }
}
