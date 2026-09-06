package com.schoolmanagement.common.audit;

import com.schoolmanagement.authentication.entity.PasswordResetToken;
import com.schoolmanagement.authentication.repository.PasswordResetTokenRepository;
import com.schoolmanagement.authentication.repository.UtilisateurRepository;
import com.schoolmanagement.authentication.service.LogService;
import java.lang.reflect.Method;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class JournalisationAspect {

  private final LogService logService;
  private final UtilisateurRepository utilisateurRepository;
  private final PasswordResetTokenRepository passwordResetTokenRepository;

  @Around(
      "@within(com.schoolmanagement.common.audit.JournaliserAutomatiquement) && execution(public * *(..))")
  public Object journaliser(ProceedingJoinPoint joinPoint) throws Throwable {
    String methode = joinPoint.getSignature().getName();
    String ressource = ressource(AopUtils.getTargetClass(joinPoint.getTarget()).getSimpleName());
    Long utilisateurId = utilisateurConnecteOuCible(joinPoint);

    try {
      Object resultat = joinPoint.proceed();
      logService.enregistrerAutomatiquement(
          action(methode, ressource, "SUCCES"), description(joinPoint, "SUCCES"), ressource, "SUCCES", utilisateurId);
      return resultat;
    } catch (Throwable exception) {
      logService.enregistrerAutomatiquement(
          action(methode, ressource, "ECHEC"), description(joinPoint, "ECHEC"), ressource, "ECHEC", utilisateurId);
      throw exception;
    }
  }

  private Long utilisateurConnecteOuCible(ProceedingJoinPoint joinPoint) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof Number principal) {
      return principal.longValue();
    }

    String methode = joinPoint.getSignature().getName();
    for (Object argument : joinPoint.getArgs()) {
      if (argument == null) continue;
      try {
        if (methode.equals("connexion")) {
          Object identifiant = valeur(argument, "identifiant");
          if (identifiant != null) {
            return utilisateurRepository.findByIdentifiant(identifiant.toString()).map(u -> u.getId()).orElse(null);
          }
        }
        if (methode.equals("demanderReinitialisation")) {
          Object email = valeur(argument, "email");
          if (email != null) {
            return utilisateurRepository.findByEmail(email.toString().trim().toLowerCase(Locale.ROOT))
                .map(u -> u.getId()).orElse(null);
          }
        }
        if (methode.equals("reinitialiserMotDePasse")) {
          Object token = valeur(argument, "token");
          if (token != null) {
            return passwordResetTokenRepository.findByToken(token.toString())
                .map(PasswordResetToken::getUser).map(u -> u.getId()).orElse(null);
          }
        }
      } catch (ReflectiveOperationException | RuntimeException ignored) {
        return null;
      }
    }
    return null;
  }

  private Object valeur(Object objet, String nom) throws ReflectiveOperationException {
    try {
      Method methode = objet.getClass().getMethod(nom);
      return methode.invoke(objet);
    } catch (NoSuchMethodException exception) {
      Method methode = objet.getClass().getMethod("get" + Character.toUpperCase(nom.charAt(0)) + nom.substring(1));
      return methode.invoke(objet);
    }
  }

  private String action(String methode, String ressource, String resultat) {
    String operation = methode.toLowerCase(Locale.ROOT);
    if (operation.equals("connexion")) {
      return resultat.equals("ECHEC") ? "ECHEC_CONNEXION" : "CONNEXION";
    }
    if (operation.equals("deconnexion")) {
      return "DECONNEXION";
    }
    String prefixe = operation.contains("creer") || operation.contains("create") ? "CREATION"
        : operation.contains("modifier") || operation.contains("update") || operation.contains("mettreajour") ? "MODIFICATION"
        : operation.contains("supprimer") || operation.contains("delete") ? "SUPPRESSION"
        : operation.contains("annuler") ? "ANNULATION"
        : operation.contains("activer") ? "ACTIVATION"
        : operation.contains("desactiver") ? "DESACTIVATION"
        : operation.contains("valider") ? "VALIDATION"
        : operation.contains("justifier") ? "JUSTIFICATION"
        : operation.contains("uploader") || operation.contains("upload") ? "TELEVERSEMENT"
        : operation.contains("generer") ? "GENERATION"
        : operation.contains("marquer") ? "MARQUAGE"
        : operation.contains("motdepasse") ? "AUTHENTIFICATION"
        : "CONSULTATION";
    return prefixe + "_" + ressource;
  }

  private String ressource(String nomService) {
    String nom = nomService.replaceFirst("Service$", "");
    return nom.toUpperCase(Locale.ROOT);
  }

  private String description(ProceedingJoinPoint joinPoint, String resultat) {
    return joinPoint.getSignature().getName() + " - resultat: " + resultat;
  }
}