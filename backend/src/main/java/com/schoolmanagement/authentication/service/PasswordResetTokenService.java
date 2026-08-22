package com.schoolmanagement.authentication.service;

import com.schoolmanagement.authentication.dto.request.PasswordResetTokenRequest;
import com.schoolmanagement.authentication.entity.PasswordResetToken;
import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.authentication.repository.PasswordResetTokenRepository;
import com.schoolmanagement.authentication.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public PasswordResetToken generateToken(Long userId) {
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        PasswordResetToken token = PasswordResetToken.builder()
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
        return tokenRepository.findByToken(tokenValue)
                .filter(token -> !token.isUsed() && token.getExpiryDate().isAfter(LocalDateTime.now()))
                .isPresent();
    }

    public boolean markTokenAsUsed(Long id) {
        return tokenRepository.findById(id).map(token -> {
            token.setUsed(true);
            tokenRepository.save(token);
            return true;
        }).orElse(false);
    }

    public boolean deleteToken(Long id) {
        return tokenRepository.findById(id).map(token -> {
            tokenRepository.delete(token);
            return true;
        }).orElse(false);
    }
}
