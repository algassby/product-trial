package com.barry.product.service;

import com.barry.product.exception.NotFoundException;
import com.barry.product.modele.User;
import com.barry.product.repository.UserRepository;
import com.barry.product.utils.jwt.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new NotFoundException("Mot de passe incorrect");
        }

        // 🔥 Génération du token JWT
        return jwtUtil.generateToken(user.getEmail(), user.getUsername());
    }
}
