package com.barry.product.controller;

import com.barry.product.annotations.ApiRestController;
import com.barry.product.dto.request.LoginRequest;
import com.barry.product.dto.request.UserRequest;
import com.barry.product.dto.response.JwtTokenResponse;
import com.barry.product.dto.response.UserResponse;
import com.barry.product.service.AuthService;
import com.barry.product.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiRestController
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
    public ResponseEntity<JwtTokenResponse> generateToken(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword()));
    }
}
