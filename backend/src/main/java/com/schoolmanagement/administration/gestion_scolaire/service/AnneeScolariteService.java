package com.schoolmanagement.administration.gestion_scolaire.service;

import com.schoolmanagement.common.audit.JournaliserAutomatiquement;

import com.schoolmanagement.administration.gestion_scolaire.dto.request.AnneeScolariteCreationRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.AnneeScolariteUpdateRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.AnneeScolariteResponse;
import com.schoolmanagement.administration.gestion_scolaire.entity.AnneeScolarite;
import com.schoolmanagement.administration.gestion_scolaire.repository.AnneeScolariteRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@JournaliserAutomatiquement
public class AnneeScolariteService {

  private final AnneeScolariteRepository anneeScolariteRepository;

  @Transactional
  public AnneeScolariteResponse creerAnneeScolarite(AnneeScolariteCreationRequest request) {
    AnneeScolarite anneeScolarite =
        AnneeScolarite.builder()
            .libelle(request.getLibelle())
            .dateDebut(request.getDateDebut())
            .dateFin(request.getDateFin())
            .statut(request.getStatut())
            .build();

    AnneeScolarite savedAnnee = anneeScolariteRepository.save(anneeScolarite);
    return mapToResponse(savedAnnee);
  }

  @Transactional(readOnly = true)
  public AnneeScolariteResponse obtenirAnneeScolariteParId(Long id) {
    AnneeScolarite annee =
        anneeScolariteRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Année scolaire introuvable avec l'ID : " + id));
    return mapToResponse(annee);
  }

  @Transactional(readOnly = true)
  public List<AnneeScolariteResponse> listerToutesLesAnneesScolaires() {
    return anneeScolariteRepository.findAll().stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Transactional
  public AnneeScolariteResponse mettreAJourAnneeScolarite(
      Long id, AnneeScolariteUpdateRequest request) {
    AnneeScolarite annee =
        anneeScolariteRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Année scolaire introuvable avec l'ID : " + id));

    if (request.getLibelle() != null) annee.setLibelle(request.getLibelle());
    if (request.getDateDebut() != null) annee.setDateDebut(request.getDateDebut());
    if (request.getDateFin() != null) annee.setDateFin(request.getDateFin());
    if (request.getStatut() != null) annee.setStatut(request.getStatut());

    AnneeScolarite updatedAnnee = anneeScolariteRepository.save(annee);
    return mapToResponse(updatedAnnee);
  }

  @Transactional
  public AnneeScolariteResponse activerAnneeScolarite(Long id) {
    AnneeScolarite annee =
        anneeScolariteRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Année scolaire introuvable avec l'ID : " + id));

    // Optionnel : Vous pouvez aussi désactiver les autres années ici si une seule doit être active
    // à la fois
    annee.setStatut("ACTIVE");

    AnneeScolarite updatedAnnee = anneeScolariteRepository.save(annee);
    return mapToResponse(updatedAnnee);
  }

  @Transactional
  public void supprimerAnneeScolarite(Long id) {
    if (!anneeScolariteRepository.existsById(id)) {
      throw new EntityNotFoundException("Année scolaire introuvable avec l'ID : " + id);
    }
    anneeScolariteRepository.deleteById(id);
  }

  private AnneeScolariteResponse mapToResponse(AnneeScolarite annee) {
    return AnneeScolariteResponse.builder()
        .id(annee.getId())
        .libelle(annee.getLibelle())
        .dateDebut(annee.getDateDebut())
        .dateFin(annee.getDateFin())
        .statut(annee.getStatut())
        .build();
  }
}
