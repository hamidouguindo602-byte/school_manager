package com.schoolmanagement.administration.gestion_scolaire.service;

import com.schoolmanagement.common.audit.JournaliserAutomatiquement;

import com.schoolmanagement.administration.emploi_temps.dto.response.ClasseResponse;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.FormationCreationRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.FormationUpdateRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.FormationResponse;
import com.schoolmanagement.administration.gestion_scolaire.entity.Formation;
import com.schoolmanagement.administration.gestion_scolaire.repository.FormationRepository;
import com.schoolmanagement.administration.gestion_scolaire.repository.InscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@JournaliserAutomatiquement
public class FormationService {

  private final FormationRepository formationRepository;
  private final InscriptionRepository inscriptionRepository;

  @Transactional
  public FormationResponse creerFormation(FormationCreationRequest request) {
    Formation formation =
        Formation.builder()
            .nomFormation(request.getNomFormation())
            .description(request.getDescription())
            .duree(request.getDuree())
            .build();

    Formation savedFormation = formationRepository.save(formation);
    return mapToResponse(savedFormation);
  }

  @Transactional(readOnly = true)
  public FormationResponse obtenirFormationParId(Long id) {
    Formation formation =
        formationRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Formation introuvable avec l'ID : " + id));
    return mapToResponse(formation);
  }

  @Transactional(readOnly = true)
  public List<FormationResponse> listerToutesLesFormations() {
    return formationRepository.findAll().stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Transactional
  public FormationResponse mettreAJourFormation(Long id, FormationUpdateRequest request) {
    Formation formation =
        formationRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Formation introuvable avec l'ID : " + id));

    if (request.getNomFormation() != null) formation.setNomFormation(request.getNomFormation());
    if (request.getDescription() != null) formation.setDescription(request.getDescription());
    if (request.getDuree() != null) formation.setDuree(request.getDuree());

    Formation updatedFormation = formationRepository.save(formation);
    return mapToResponse(updatedFormation);
  }

  @Transactional
  public void supprimerFormation(Long id) {
    if (!formationRepository.existsById(id)) {
      throw new EntityNotFoundException("Formation introuvable avec l'ID : " + id);
    }
    formationRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public List<ClasseResponse> obtenirClassesParFormation(Long id) {
    if (!formationRepository.existsById(id)) {
      throw new EntityNotFoundException("Formation introuvable avec l'ID : " + id);
    }
    return inscriptionRepository.findByFormationId(id).stream()
        .map(inscription -> inscription.getClasse())
        .collect(
            Collectors.toMap(
                classe -> classe.getId(),
                classe -> {
                  ClasseResponse response = new ClasseResponse();
                  response.setId(classe.getId());
                  response.setNomClasse(classe.getNomClasse());
                  response.setNiveau(classe.getNiveau());
                  return response;
                },
                (first, second) -> first,
                LinkedHashMap::new))
        .values()
        .stream()
        .toList();
  }

  private FormationResponse mapToResponse(Formation formation) {
    return FormationResponse.builder()
        .id(formation.getId())
        .nomFormation(formation.getNomFormation())
        .description(formation.getDescription())
        .duree(formation.getDuree())
        .build();
  }
}
