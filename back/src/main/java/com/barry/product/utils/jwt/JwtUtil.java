package com.barry.product.utils.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = generateSecretKey(); // 🔐 À stocker en variable d'env !

    @Value("${spring.application.name}")
    private String apiName;

    private static final long EXPIRATION_TIME = 3600000; // 10 heures

    // ✅ Génère un token pour un utilisateur
    public String generateToken(String email, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username); // Ajout d'informations dans le token
        return createToken(claims, email);
    }

    // 🔐 Crée le token JWT signé
    private String createToken(Map<String, Object> claims, String subject) {
        long nowMillis = System.currentTimeMillis();
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuer(apiName)
                .issuedAt(new Date(EXPIRATION_TIME))
                .expiration(new Date(nowMillis + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    public String getUsername(String token){

        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token){
        Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parse(token);
        return true;

    }

    // Générer une clé sécurisée (Base64)
    private static String generateSecretKey() {
        Key key = Jwts.SIG.HS256.key().build(); // Génère une clé de 256 bits
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
}
