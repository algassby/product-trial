package com.barry.product.service.impl;

import com.barry.product.dto.response.JwtTokenResponse;
import com.barry.product.exception.NotFoundException;
import com.barry.product.modele.User;
import com.barry.product.repository.UserRepository;
import com.barry.product.service.auth.AuthService;
import com.barry.product.utils.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public JwtTokenResponse authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new NotFoundException("Mot de passe incorrect");
        }

        return new JwtTokenResponse(user.getEmail(), jwtUtil.generateToken(user));
    }
}
