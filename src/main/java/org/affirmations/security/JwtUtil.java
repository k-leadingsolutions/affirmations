package org.affirmations.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration}")
    private long EXPIRATION;

    private Key getSigningKey() {
        logger.debug("Getting signing key for JWT");
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String username, String role) {
        logger.info("Generating JWT token for user: {} with role: {}", username, role);
        String token = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        logger.debug("JWT token generated for user: {}", username);
        return token;
    }

    public String extractUsername(String token) {
        logger.debug("Extracting username from JWT token");
        try {
            String username = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            logger.info("Extracted username: {}", username);
            return username;
        } catch (Exception e) {
            logger.error("Failed to extract username from JWT token", e);
            throw e;
        }
    }

    public String extractRole(String token) {
        logger.debug("Extracting role from JWT token");
        try {
            String role = (String) Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("role");
            logger.info("Extracted role: {}", role);
            return role;
        } catch (Exception e) {
            logger.error("Failed to extract role from JWT token", e);
            throw e;
        }
    }

    public boolean validateToken(String token) {
        logger.debug("Validating JWT token");
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            logger.info("JWT token is valid");
            return true;
        } catch (Exception e) {
            logger.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }
}