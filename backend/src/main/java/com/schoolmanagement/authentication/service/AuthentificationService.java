package com.schoolmanagement.authentication.service;

import com.schoolmanagement.authentication.dto.request.ConnexionRequest;
import com.schoolmanagement.authentication.dto.request.DemandeReinitialisationMotDePasseRequest;
import com.schoolmanagement.authentication.dto.request.ModificationMotDePasseRequest;
import com.schoolmanagement.authentication.dto.request.ReinitialisationMotDePasseRequest;
import com.schoolmanagement.authentication.dto.response.ConnexionResponse;
import com.schoolmanagement.authentication.entity.PasswordResetToken;
import com.schoolmanagement.authentication.entity.StatutUtilisateur;
import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.authentication.repository.PasswordResetTokenRepository;
import com.schoolmanagement.authentication.repository.UtilisateurRepository;
import com.schoolmanagement.authentication.securite.ServiceJeton;
import com.schoolmanagement.common.exception.AuthentificationException;
import com.schoolmanagement.common.audit.JournaliserAutomatiquement;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@JournaliserAutomatiquement
@RequiredArgsConstructor
public class AuthentificationService {
  private final PasswordEncoder passwordEncoder;
  private final UtilisateurRepository utilisateurRepository;
  private final ServiceJeton serviceJeton;
  private final PasswordResetTokenRepository passwordResetTokenRepository;
  private final EmailService emailService;

  public ConnexionResponse connexion(ConnexionRequest request) {
    // etape 1: verifier que l'utilisateur existe dans la base.
    Utilisateur utilisateur =
        utilisateurRepository
            .findByIdentifiant(request.identifiant())
            .orElseThrow(
                () -> new AuthentificationException("Identifiant ou mot de passe incorrect"));
    // Étape 2 : vérifier que le compte est actif
    if (utilisateur.getStatut() != StatutUtilisateur.ACTIF) {
      throw new AuthentificationException("Le compte est inactif");
    }

    // Étape 3 : vérifier le mot de passe
    if (!passwordEncoder.matches(request.motDePasse(), utilisateur.getMotDePasse())) {

      throw new AuthentificationException("Identifiant ou mot de passe incorrect");
    }
    // Creation du token
    String token = serviceJeton.genererToken(utilisateur.getId(), utilisateur.getTypeRole());

    return new ConnexionResponse(token, utilisateur.getTypeRole());
  }

  public String deconnexion() {
    SecurityContextHolder.clearContext();
    return "Deconnexion reussie";
  }

  public String modifierMotDePasse(ModificationMotDePasseRequest request) {

    // Récupérer l'ID de l'utilisateur connecté
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (!(principal instanceof Number)) {
      throw new AuthentificationException("Utilisateur non authentifie");
    }

    Long utilisateurId = ((Number) principal).longValue();

    // Récupérer l'utilisateur
    Utilisateur utilisateur =
        utilisateurRepository
            .findById(utilisateurId)
            .orElseThrow(() -> new AuthentificationException("Utilisateur introuvable"));

    // Vérifier l'ancien mot de passe
    if (!passwordEncoder.matches(request.ancienMotDePasse(), utilisateur.getMotDePasse())) {

      throw new AuthentificationException("Mot de passe incorrect");
    }

    // Encoder et sauvegarder le nouveau mot de passe
    utilisateur.setMotDePasse(passwordEncoder.encode(request.nouveauMotDePasse()));

    utilisateurRepository.save(utilisateur);

    return "Mot de passe modifié avec succès";
  }

  public String demanderReinitialisation(DemandeReinitialisationMotDePasseRequest request) {

    utilisateurRepository
        .findByEmail(request.email().trim().toLowerCase())
        .ifPresent(
            utilisateur -> {
              String token = UUID.randomUUID().toString();
              PasswordResetToken passwordResetToken =
                  PasswordResetToken.builder()
                      .token(token)
                      .expiryDate(LocalDateTime.now().plusMinutes(15))
                      .used(false)
                      .user(utilisateur)
                      .build();

              passwordResetTokenRepository.save(passwordResetToken);
              emailService.envoyerLienReinitialisation(utilisateur.getEmail(), token);
            });

    return "Si un compte correspond à cette adresse, un lien de réinitialisation a été envoyé";
  }

  public String reinitialiserMotDePasse(ReinitialisationMotDePasseRequest request) {

    PasswordResetToken resetToken =
        passwordResetTokenRepository
            .findByToken(request.token())
            .orElseThrow(() -> new AuthentificationException("Token de réinitialisation invalide"));

    // Vérifier si le token a déjà été utilisé
    if (resetToken.isUsed()) {
      throw new AuthentificationException("Ce token a déjà été utilisé");
    }

    // Vérifier si le token a expiré
    if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
      throw new AuthentificationException("Le token de réinitialisation a expiré");
    }

    // Récupérer l'utilisateur associé au token
    Utilisateur utilisateur = resetToken.getUser();

    // Encoder le nouveau mot de passe
    utilisateur.setMotDePasse(passwordEncoder.encode(request.nouveauMotDePasse()));

    // Sauvegarder le nouveau mot de passe
    utilisateurRepository.save(utilisateur);

    // Invalider le token
    resetToken.setUsed(true);
    passwordResetTokenRepository.save(resetToken);

    return "Mot de passe réinitialisé avec succès";
  }
}
