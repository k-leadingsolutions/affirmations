package org.affirmations.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    private final String SECRET = "mysecretkeymysecretkeymysecretkey99";
    private final long EXPIRATION = 3600000; // 1 hour in ms
    private final String USERNAME = "testuser";
    private final String ROLE = "USER";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "SECRET", SECRET);
        ReflectionTestUtils.setField(jwtUtil, "EXPIRATION", EXPIRATION);
    }

    @Test
    void generateToken_containsUsernameAndRole() {
        String token = jwtUtil.generateToken(USERNAME, ROLE);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals(USERNAME, claims.getSubject());
        assertEquals(ROLE, claims.get("role"));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void extractUsername_returnsUsername() {
        String token = jwtUtil.generateToken(USERNAME, ROLE);
        String extracted = jwtUtil.extractUsername(token);
        assertEquals(USERNAME, extracted);
    }

    @Test
    void extractRole_returnsRole() {
        String token = jwtUtil.generateToken(USERNAME, ROLE);
        String extracted = jwtUtil.extractRole(token);
        assertEquals(ROLE, extracted);
    }

    @Test
    void validateToken_returnsTrueForValidToken() {
        String token = jwtUtil.generateToken(USERNAME, ROLE);
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void validateToken_returnsFalseForTamperedToken() {
        String token = jwtUtil.generateToken(USERNAME, ROLE);
        // Tamper the token
        String tampered = token.substring(0, token.length() - 2) + "xx";
        assertFalse(jwtUtil.validateToken(tampered));
    }

    @Test
    void validateToken_returnsFalseForExpiredToken() {
        // Manually generate an expired token using JwtUtil's signing key
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
        String expiredToken = Jwts.builder()
                .setSubject(USERNAME)
                .claim("role", ROLE)
                .setIssuedAt(new Date(System.currentTimeMillis() - 7200000)) // issued 2 hours ago
                .setExpiration(new Date(System.currentTimeMillis() - 3600000)) // expired 1 hour ago
                .signWith(key)
                .compact();
        assertFalse(jwtUtil.validateToken(expiredToken));
    }
}