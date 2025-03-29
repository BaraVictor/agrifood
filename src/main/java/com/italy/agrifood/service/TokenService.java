package com.italy.agrifood.service;

import com.italy.agrifood.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  private static final String SECRET_KEY = "your-secret-key"; // Replace with a secure key

  // Generate token for password reset
  public String generateToken(User user) {
    return Jwts.builder()
        .setSubject(user.getEmail())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiration
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  // Extract email from token
  public String extractEmailFromToken(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }
}
