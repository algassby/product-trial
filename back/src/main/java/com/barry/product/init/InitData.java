package com.barry.product.init;

import com.barry.product.dto.request.UserRequest;
import com.barry.product.mapper.UserMapper;
import com.barry.product.modele.Role;
import com.barry.product.modele.RoleEnum;
import com.barry.product.repository.RoleRepository;
import com.barry.product.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final Environment environment;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initRole();
        initAdmin();
    }

    private void initRole(){

        roleRepository.saveAll(
                Arrays.stream(RoleEnum.values())
                        .filter(roleName -> !roleRepository.existsByName(roleName.name()))
                        .map(roleName -> Role.builder().name(roleName.name()).build())
                        .toList()
        );

    }

    private void initAdmin(){
        var userRequest = UserRequest.builder()
                .email(environment.getProperty("application.admin.email"))
                .firstname("Algassimou")
                .username("admin")
                .build();

        var user = userMapper.toUser(userRequest);

        user.setPassword(passwordEncoder.encode(environment.getProperty("application.password")));
        roleRepository.findByName(RoleEnum.USER.name()).ifPresent( role-> user.getRoles().add(role));
                roleRepository.findByName(RoleEnum.ADMIN.name()).ifPresent( role-> user.getRoles().add(role));

        if(!userRepository.existsByEmail(userRequest.getEmail()) || !userRepository.existsByUsername(userRequest.getUsername())){
            userRepository.save(user);
        }
    }

}
