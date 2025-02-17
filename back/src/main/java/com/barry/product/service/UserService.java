package com.barry.product.service;

import com.barry.product.dto.request.UserRequest;
import com.barry.product.dto.response.UserResponse;
import com.barry.product.exception.AlreadyExistsException;
import com.barry.product.mapper.UserMapper;
import com.barry.product.modele.RoleEnum;
import com.barry.product.modele.User;
import com.barry.product.repository.RoleRepository;
import com.barry.product.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private final RoleRepository  roleRepository;

    public UserResponse createUser(UserRequest userRequest) {

        if(userRepository.existsByEmail(userRequest.getEmail())){
            throw new AlreadyExistsException("User already exists with email " +  userRequest.getUsername());
        }

        if(userRepository.existsByUsername(userRequest.getUsername())){
            throw new AlreadyExistsException("User already exists with username " +  userRequest.getUsername());
        }

        User user = userMapper.toUser(userRequest);

        roleRepository.findByName(RoleEnum.ROLE_USER.name()).ifPresent(role->{
                 user.getRoles().add(role);
                }

        );
        user.setPassword(passwordEncoder.encode(userRequest.getPassword())); // Hashage du mot de passe
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
}
