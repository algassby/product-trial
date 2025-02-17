package com.barry.product.service;

import com.barry.product.dto.request.UserRequest;
import com.barry.product.dto.response.UserResponse;
import com.barry.product.mapper.UserMapper;
import com.barry.product.modele.User;
import com.barry.product.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserResponse createUser(UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword())); // Hashage du mot de passe
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
}
