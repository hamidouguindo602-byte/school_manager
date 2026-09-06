package com.schoolmanagement.comptabilite.service;

import com.schoolmanagement.common.audit.JournaliserAutomatiquement;

import com.schoolmanagement.administration.gestion_scolaire.entity.Inscription;
import com.schoolmanagement.administration.gestion_scolaire.repository.InscriptionRepository;
import com.schoolmanagement.common.exception.ResourceNotFoundException;
import com.schoolmanagement.comptabilite.dto.request.EcheanceRequest;
import com.schoolmanagement.comptabilite.dto.response.EcheanceResponse;
import com.schoolmanagement.comptabilite.entity.Echeance;
import com.schoolmanagement.comptabilite.entity.StatutEcheance;
import com.schoolmanagement.comptabilite.repository.EcheanceRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@JournaliserAutomatiquement
public class EcheanceService {

  private final EcheanceRepository echeanceRepository;
  private final InscriptionRepository inscriptionRepository;

  public EcheanceService(
      EcheanceRepository echeanceRepository, InscriptionRepository inscriptionRepository) {
    this.echeanceRepository = echeanceRepository;
    this.inscriptionRepository = inscriptionRepository;
  }

  @Transactional(readOnly = true)
  public List<EcheanceResponse> obtenirToutesLesEcheances() {

    return echeanceRepository.findAll().stream().map(this::convertirEnResponse).toList();
  }

  @Transactional(readOnly = true)
  public EcheanceResponse obtenirEcheance(Long id) {

    Echeance echeance =
        echeanceRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Échéance", id));

    return convertirEnResponse(echeance);
  }

  public EcheanceResponse creerEcheance(EcheanceRequest request) {

    Inscription inscription =
        inscriptionRepository
            .findById(request.idInscription())
            .orElseThrow(
                () -> new ResourceNotFoundException("Inscription", request.idInscription()));

    Echeance echeance = new Echeance();

    echeance.setInscription(inscription);
    echeance.setLibelle(request.libelle());
    echeance.setMontant(request.montant());
    echeance.setDateLimite(request.dateLimite());

    // Une nouvelle échéance est toujours impayée.
    echeance.setStatut(StatutEcheance.IMPAYEE);

    Echeance echeanceEnregistree = echeanceRepository.save(echeance);

    return convertirEnResponse(echeanceEnregistree);
  }

  public EcheanceResponse modifierEcheance(Long id, EcheanceRequest request) {

    Echeance echeance =
        echeanceRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Échéance", id));

    Inscription inscription =
        inscriptionRepository
            .findById(request.idInscription())
            .orElseThrow(
                () -> new ResourceNotFoundException("Inscription", request.idInscription()));

    echeance.setInscription(inscription);
    echeance.setLibelle(request.libelle());
    echeance.setMontant(request.montant());
    echeance.setDateLimite(request.dateLimite());

    // Le statut n'est volontairement pas modifié ici.
    // Il sera géré automatiquement par les paiements.

    Echeance echeanceModifiee = echeanceRepository.save(echeance);

    return convertirEnResponse(echeanceModifiee);
  }

  public void supprimerEcheance(Long id) {

    Echeance echeance =
        echeanceRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Échéance", id));

    echeanceRepository.delete(echeance);
  }

  @Transactional(readOnly = true)
  public List<EcheanceResponse> obtenirEcheancesParInscription(Long idInscription) {

    if (!inscriptionRepository.existsById(idInscription)) {
      throw new ResourceNotFoundException("Inscription", idInscription);
    }

    return echeanceRepository.findByInscriptionId(idInscription).stream()
        .map(this::convertirEnResponse)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<EcheanceResponse> obtenirEcheancesEnRetard() {

    return echeanceRepository
        .findByDateLimiteBeforeAndStatutNot(LocalDate.now(), StatutEcheance.PAYEE)
        .stream()
        .map(this::convertirEnResponse)
        .toList();
  }

  private EcheanceResponse convertirEnResponse(Echeance echeance) {

    return new EcheanceResponse(
        echeance.getIdEcheance(),
        echeance.getInscription().getId(),
        echeance.getLibelle(),
        echeance.getMontant(),
        echeance.getDateLimite(),
        echeance.getStatut());
  }
}
