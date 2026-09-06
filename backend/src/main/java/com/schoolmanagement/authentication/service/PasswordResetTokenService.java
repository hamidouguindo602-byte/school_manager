package com.schoolmanagement.authentication.service;

import com.schoolmanagement.common.audit.JournaliserAutomatiquement;

import com.schoolmanagement.authentication.entity.PasswordResetToken;
import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.authentication.repository.PasswordResetTokenRepository;
import com.schoolmanagement.authentication.repository.UtilisateurRepository;
import com.schoolmanagement.common.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@JournaliserAutomatiquement
public class PasswordResetTokenService {

  private final PasswordResetTokenRepository tokenRepository;
  private final UtilisateurRepository utilisateurRepository;

  public PasswordResetToken generateToken(Long userId) {
    Utilisateur user =
        utilisateurRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", userId));

    PasswordResetToken token =
        PasswordResetToken.builder()
            .token(UUID.randomUUID().toString())
            .expiryDate(LocalDateTime.now().plusMinutes(30))
            .used(false)
            .user(user)
            .build();

    return tokenRepository.save(token);
  }

  public List<PasswordResetToken> getAllTokens() {
    return tokenRepository.findAll();
  }

  public Optional<PasswordResetToken> getTokenById(Long id) {
    return tokenRepository.findById(id);
  }

  public boolean validateToken(String tokenValue) {
    return tokenRepository
        .findByToken(tokenValue)
        .filter(token -> !token.isUsed() && token.getExpiryDate().isAfter(LocalDateTime.now()))
        .isPresent();
  }

  public boolean markTokenAsUsed(Long id) {
    return tokenRepository
        .findById(id)
        .map(
            token -> {
              token.setUsed(true);
              tokenRepository.save(token);
              return true;
            })
        .orElse(false);
  }

  public boolean deleteToken(Long id) {
    return tokenRepository
        .findById(id)
        .map(
            token -> {
              tokenRepository.delete(token);
              return true;
            })
        .orElse(false);
  }
}
