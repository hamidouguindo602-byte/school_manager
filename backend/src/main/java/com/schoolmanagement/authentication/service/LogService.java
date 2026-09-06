package com.schoolmanagement.authentication.service;

import com.schoolmanagement.authentication.dto.request.LogRequest;
import com.schoolmanagement.authentication.entity.Log;
import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.authentication.repository.LogRepository;
import com.schoolmanagement.authentication.repository.UtilisateurRepository;
import com.schoolmanagement.common.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

  private final LogRepository logRepository;
  private final UtilisateurRepository utilisateurRepository;

  // 🔹 Enregistrer une action
  public Log enregistrerAction(String action, Long utilisateurId) {
    return enregistrerAutomatiquement(action, action, "AUTHENTIFICATION", "SUCCES", utilisateurId);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Log enregistrerAutomatiquement(
      String action, String description, String ressource, String resultat, Long utilisateurId) {
    Utilisateur utilisateur = utilisateurId == null ? null : utilisateurRepository.findById(utilisateurId).orElse(null);
    return logRepository.save(
        Log.builder()
            .action(action)
            .description(description)
            .ressource(ressource)
            .resultat(resultat)
            .dateAction(LocalDateTime.now())
            .utilisateur(utilisateur)
            .build());
  }

  // 🔹 Créer un log via DTO
  public Log createLog(LogRequest request) {

    Utilisateur utilisateur =
        utilisateurRepository
            .findById(request.getUtilisateurId())
            .orElseThrow(
                () -> new ResourceNotFoundException("Utilisateur", request.getUtilisateurId()));

    Log log =
        Log.builder()
            .action(request.getAction())
          .description(request.getDescription() == null ? request.getAction() : request.getDescription())
          .ressource(request.getRessource() == null ? "MANUEL" : request.getRessource())
          .resultat(request.getResultat() == null ? "SUCCES" : request.getResultat())
            .dateAction(LocalDateTime.now())
            .utilisateur(utilisateur)
            .build();

    return logRepository.save(log);
  }

  // 🔹 Récupérer tous les logs
  public List<Log> getAllLogs() {
    return logRepository.findAll();
  }

  // 🔹 Récupérer un log par ID
  public Log getLogById(Long id) {
    return logRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Log", id));
  }

  // 🔹 Supprimer un log
  public boolean deleteLog(Long id) {
    if (!logRepository.existsById(id)) {
      throw new ResourceNotFoundException("Log", id);
    }
    logRepository.deleteById(id);
    return true;
  }
}
