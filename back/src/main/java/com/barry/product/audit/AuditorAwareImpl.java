package com.barry.product.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
      git   
        return Optional.of("system"); // Remplace "system" par le vrai utilisateur si nécessaire
    }
}
