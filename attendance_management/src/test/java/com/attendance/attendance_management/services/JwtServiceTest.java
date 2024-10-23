package com.attendance.attendance_management.services;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private final String secretKey = "secretkeyforusermjhkghkrtyujfghjukyughkhjljhkluiyouio";
    private String token;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(secretKey);
        token = Jwts.builder()
                .subject("john")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(
                        SignatureAlgorithm.HS256, secretKey
                ).compact();
    }

    @Test
    void TestGetToken() {
        String generatedToken = jwtService.getToken("john");
        assertNotNull(generatedToken);
        assertFalse(generatedToken.isEmpty());
    }

    @Test
    void TestExtractUserName() {
        String extractedUsername = jwtService.extractUserName(token);
        assertEquals("john", extractedUsername); // should match the subject used in token
    }

    @Test
    void TestValidateToken() {
        when(userDetails.getUsername()).thenReturn("john");
        boolean isValid = jwtService.validateToken(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void TestValidateTokenWithExpiredToken() {
        String expiredToken = Jwts.builder()
                .subject("john")
                .issuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 11)) // 11 hours ago
                .expiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60)) // expired 1 hour ago
                .signWith(
                        SignatureAlgorithm.HS256, secretKey
                ).compact();
        assertThrows(ExpiredJwtException.class, () -> {
            jwtService.validateToken(expiredToken, userDetails);
        });
    }
}
