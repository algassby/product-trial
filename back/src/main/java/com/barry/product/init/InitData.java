package com.barry.product.init;

import com.barry.product.modele.Role;
import com.barry.product.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        initRole();
    }

    private void initRole(){


        List<String> roleNames = List.of("ROLE_USER", "ROLE_ADMIN", "CUSTOMER");
        roleRepository.saveAll(
                roleNames.stream()
                        .filter(roleName -> !roleRepository.existsByName(roleName))
                        .map(roleName -> Role.builder().name(roleName).build())
                        .toList()
        );


    }


}
