package com.schoolmanagement.administration.gestion_scolaire.service;

import com.schoolmanagement.common.audit.JournaliserAutomatiquement;

import com.schoolmanagement.administration.emploi_temps.entity.Classe;
import com.schoolmanagement.administration.emploi_temps.repository.ClasseRepository;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.InscriptionRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.InscriptionResponse;
import com.schoolmanagement.administration.gestion_scolaire.entity.Eleve;
import com.schoolmanagement.administration.gestion_scolaire.entity.Formation;
import com.schoolmanagement.administration.gestion_scolaire.entity.Inscription;
import com.schoolmanagement.administration.gestion_scolaire.entity.StatusInscription;
import com.schoolmanagement.administration.gestion_scolaire.repository.EleveRepository;
import com.schoolmanagement.administration.gestion_scolaire.repository.FormationRepository;
import com.schoolmanagement.administration.gestion_scolaire.repository.InscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@JournaliserAutomatiquement
public class InscriptionService {

  private final InscriptionRepository inscriptionRepository;
  private final EleveRepository eleveRepository;
  private final FormationRepository formationRepository;
  private final ClasseRepository classeRepository;

  @Transactional
  public InscriptionResponse creerInscription(InscriptionRequest request) {
    Eleve eleve =
        eleveRepository
            .findById(request.getIdEleve())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Élève non trouvé avec l'ID : " + request.getIdEleve()));

    Formation formation =
        formationRepository
            .findById(request.getIdFormation())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Formation non trouvée avec l'ID : " + request.getIdFormation()));

    Classe classe =
        classeRepository
            .findById(request.getIdClasse())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Classe non trouvée avec l'ID : " + request.getIdClasse()));

    Inscription inscription =
        Inscription.builder()
            .eleve(eleve)
            .formation(formation)
            .classe(classe)
            .dateInscription(
                request.getDateInscription() != null
                    ? request.getDateInscription()
                    : LocalDate.now())
            .statut(
                request.getStatut() != null
                    ? request.getStatut().name()
                    : StatusInscription.en_attente.name())
            .build();

    Inscription saved = inscriptionRepository.save(inscription);
    return mapToResponse(saved);
  }

  @Transactional
  public InscriptionResponse validerInscription(Long id) {
    Inscription inscription =
        inscriptionRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Inscription non trouvée avec l'ID : " + id));

    inscription.setStatut(StatusInscription.valide.name());
    Inscription updated = inscriptionRepository.save(inscription);
    return mapToResponse(updated);
  }

  @Transactional
  public InscriptionResponse annulerInscription(Long id) {
    Inscription inscription =
        inscriptionRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Inscription non trouvée avec l'ID : " + id));

    inscription.setStatut(StatusInscription.annule.name());
    Inscription updated = inscriptionRepository.save(inscription);
    return mapToResponse(updated);
  }

  @Transactional(readOnly = true)
  public List<InscriptionResponse> listerToutesLesInscriptions() {
    return inscriptionRepository.findAll().stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public InscriptionResponse recupererParId(Long id) {
    Inscription inscription =
        inscriptionRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Inscription non trouvée avec l'ID : " + id));
    return mapToResponse(inscription);
  }

  @Transactional
  public InscriptionResponse mettreAJourInscription(Long id, InscriptionRequest request) {
    Inscription inscription =
        inscriptionRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Inscription non trouvée avec l'ID : " + id));

    if (request.getIdEleve() != null) {
      Eleve eleve =
          eleveRepository
              .findById(request.getIdEleve())
              .orElseThrow(
                  () ->
                      new EntityNotFoundException(
                          "Élève non trouvé avec l'ID : " + request.getIdEleve()));
      inscription.setEleve(eleve);
    }

    if (request.getIdFormation() != null) {
      Formation formation =
          formationRepository
              .findById(request.getIdFormation())
              .orElseThrow(
                  () ->
                      new EntityNotFoundException(
                          "Formation non trouvée avec l'ID : " + request.getIdFormation()));
      inscription.setFormation(formation);
    }

    if (request.getIdClasse() != null) {
      Classe classe =
          classeRepository
              .findById(request.getIdClasse())
              .orElseThrow(
                  () ->
                      new EntityNotFoundException(
                          "Classe non trouvée avec l'ID : " + request.getIdClasse()));
      inscription.setClasse(classe);
    }

    if (request.getDateInscription() != null) {
      inscription.setDateInscription(request.getDateInscription());
    }

    if (request.getStatut() != null) {
      inscription.setStatut(request.getStatut().name());
    }

    Inscription updated = inscriptionRepository.save(inscription);
    return mapToResponse(updated);
  }

  @Transactional
  public void supprimerInscription(Long id) {
    if (!inscriptionRepository.existsById(id)) {
      throw new EntityNotFoundException("Inscription non trouvée avec l'ID : " + id);
    }
    inscriptionRepository.deleteById(id);
  }

  private InscriptionResponse mapToResponse(Inscription inscription) {
    return InscriptionResponse.builder()
        .idInscription(inscription.getId())
        .idEleve(inscription.getEleve() != null ? inscription.getEleve().getId() : null)
        .idFormation(inscription.getFormation() != null ? inscription.getFormation().getId() : null)
        .idClasse(inscription.getClasse() != null ? inscription.getClasse().getId() : null)
        .dateInscription(inscription.getDateInscription())
        .statut(
            inscription.getStatut() != null
                ? StatusInscription.valueOf(inscription.getStatut())
                : null)
        .build();
  }
}
