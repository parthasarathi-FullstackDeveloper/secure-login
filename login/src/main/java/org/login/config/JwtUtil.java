package org.login.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private String secretKey = "your-secret-key"; // Store it securely, such as in application.properties

    // Generate a JWT token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration
                .signWith(SignatureAlgorithm.HS256, getSigningKey())
                .compact();
    }

    // Extract the username from the token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Check if the token is expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract the expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    // Extract the claims from the token using Jwts.parser() for version 0.9.1
    private Claims extractClaims(String token) {
        return Jwts.parser()  // Use the older parser() method for version 0.9.1
                .setSigningKey(getSigningKey())  // Set the signing key for verification
                .parseClaimsJws(token)  // Parse the JWT token
                .getBody();  // Get the body of the JWT
    }

    // Validate the token
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    // Get the signing key (use secure key generation)
    private Key getSigningKey() {
        return MacProvider.generateKey();  // Use MacProvider for HMAC key generation
    }

    // Extract token from the HTTP request header
    public String extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Remove "Bearer " prefix and return the token
        }
        return null;  // Return null if token is not present
    }
}
