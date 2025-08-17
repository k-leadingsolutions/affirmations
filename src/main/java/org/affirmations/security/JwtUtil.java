package org.affirmations.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration}")
    private long EXPIRATION;

    private Key getSigningKey() {
        log.debug("Getting signing key for JWT");
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String username, String role) {
        log.info("Generating JWT token for user: {} with role: {}", username, role);
        String token = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        log.debug("JWT token generated for user: {}", username);
        return token;
    }

    public String extractUsername(String token) {
        log.debug("Extracting username from JWT token");
        try {
            String username = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            log.info("Extracted username: {}", username);
            return username;
        } catch (Exception e) {
            log.error("Failed to extract username from JWT token", e);
            throw e;
        }
    }

    public String extractRole(String token) {
        log.debug("Extracting role from JWT token");
        try {
            String role = (String) Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("role");
            log.info("Extracted role: {}", role);
            return role;
        } catch (Exception e) {
            log.error("Failed to extract role from JWT token", e);
            throw e;
        }
    }

    public boolean validateToken(String token) {
        log.debug("Validating JWT token");
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            log.info("JWT token is valid");
            return true;
        } catch (Exception e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }
}