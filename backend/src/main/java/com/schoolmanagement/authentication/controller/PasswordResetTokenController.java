package com.schoolmanagement.authentication.controller;

import com.schoolmanagement.authentication.dto.request.PasswordResetTokenRequest;
import com.schoolmanagement.authentication.dto.response.PasswordResetTokenResponse;
import com.schoolmanagement.authentication.entity.PasswordResetToken;
import com.schoolmanagement.authentication.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/password-reset-tokens")
public class PasswordResetTokenController {

    @Autowired
    private PasswordResetTokenService tokenService;

    @GetMapping
    public List<PasswordResetTokenResponse> getAllTokens() {
        return tokenService.getAllTokens().stream()
                .map(token -> PasswordResetTokenResponse.builder()
                        .id(token.getId())
                        .token(token.getToken())
                        .expiryDate(token.getExpiryDate())
                        .used(token.isUsed())
                        .userId(token.getUser().getId())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasswordResetTokenResponse> getTokenById(@PathVariable Long id) {
        return tokenService.getTokenById(id)
                .map(token -> ResponseEntity.ok(
                        PasswordResetTokenResponse.builder()
                                .id(token.getId())
                                .token(token.getToken())
                                .expiryDate(token.getExpiryDate())
                                .used(token.isUsed())
                                .userId(token.getUser().getId())
                                .build()
                ))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PasswordResetTokenResponse> createToken(@RequestBody PasswordResetTokenRequest request) {
        PasswordResetToken token = tokenService.generateToken(request.getUserId());
        PasswordResetTokenResponse response = PasswordResetTokenResponse.builder()
                .id(token.getId())
                .token(token.getToken())
                .expiryDate(token.getExpiryDate())
                .used(token.isUsed())
                .userId(token.getUser().getId())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> markTokenAsUsed(@PathVariable Long id) {
        boolean updated = tokenService.markTokenAsUsed(id);
        return updated ? ResponseEntity.ok("Token marqué comme utilisé")
                       : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteToken(@PathVariable Long id) {
        boolean deleted = tokenService.deleteToken(id);
        return deleted ? ResponseEntity.ok("Token supprimé")
                       : ResponseEntity.notFound().build();
    }
}
