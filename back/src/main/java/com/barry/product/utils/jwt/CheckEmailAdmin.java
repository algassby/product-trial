package com.barry.product.utils.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class CheckEmailAdmin implements Predicate<String> {

    private final Environment environment;
    @Override
    public boolean test(String s) {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName().equals(environment.getProperty("application.admin"));
    }
}
