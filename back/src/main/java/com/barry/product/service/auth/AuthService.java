package com.barry.product.service.auth;

import com.barry.product.dto.response.JwtTokenResponse;

public interface AuthService {
    JwtTokenResponse authenticateUser(String email, String password);
}
