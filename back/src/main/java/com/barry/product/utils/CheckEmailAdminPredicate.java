package com.barry.product.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class CheckEmailAdminPredicate implements Predicate<String> {

    private final Environment environment;
    @Override
    public boolean test(String email) {
        return email.equals(environment.getProperty("application.admin.email"));
    }
}
