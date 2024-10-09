package com.attendance.attendance_management.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private final String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    public JwtService() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey(); // generate key using algorithm
            this.secretKey = Base64.getEncoder().encodeToString(sk.getEncoded()); // encoded to string
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public String getToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder().claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +jwtExpirationMs)).and().signWith(getkey()).compact();

    }

    private SecretKey getkey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());   //to create secure sign key
    }


    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimsTFunction) {
        Claims claims = extractAllClaims(token);
        return  claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return  Jwts.parser()
                .verifyWith(getkey())   // retrieve secret key
                .build()
                .parseSignedClaims(token) //verify the token
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
