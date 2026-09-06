package com.schoolmanagement.authentication.securite;

import com.schoolmanagement.authentication.entity.TypeRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServiceJeton {

  private final SecretKey secretKey;
  private final long expiration;

  public ServiceJeton(
      @Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expiration) {

    if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
      throw new IllegalStateException("JWT_SECRET doit contenir au moins 32 octets");
    }
    if (expiration <= 0) {
      throw new IllegalStateException("JWT_EXPIRATION doit etre strictement positif");
    }

    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    this.expiration = expiration;
  }

  public String genererToken(Long utilisateurId, TypeRole role) {

    Date maintenant = new Date();

    Date expirationDate = new Date(maintenant.getTime() + expiration);

    return Jwts.builder()
        .subject(utilisateurId.toString())
        .claim("role", role.name())
        .issuedAt(maintenant)
        .expiration(expirationDate)
        .signWith(secretKey)
        .compact();
  }

  public Claims verifierToken(String token) {

    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
  }
}
