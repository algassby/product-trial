package com.barry.product.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Récupérer l’utilisateur connecté (exemple avec Spring Security)
        // return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());

        // Si Spring Security n’est pas utilisé, retourne un utilisateur par défaut
        return Optional.of("system"); // Remplace "system" par le vrai utilisateur si nécessaire
    }
}
