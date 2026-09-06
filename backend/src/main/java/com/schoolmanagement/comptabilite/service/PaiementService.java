package com.schoolmanagement.comptabilite.service;

import com.schoolmanagement.common.audit.JournaliserAutomatiquement;

import com.schoolmanagement.common.exception.ResourceNotFoundException;
import com.schoolmanagement.comptabilite.dto.request.PaiementRequest;
import com.schoolmanagement.comptabilite.dto.response.PaiementResponse;
import com.schoolmanagement.comptabilite.entity.Echeance;
import com.schoolmanagement.comptabilite.entity.Paiement;
import com.schoolmanagement.comptabilite.entity.StatutEcheance;
import com.schoolmanagement.comptabilite.entity.StatutPaiement;
import com.schoolmanagement.comptabilite.repository.EcheanceRepository;
import com.schoolmanagement.comptabilite.repository.PaiementRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@JournaliserAutomatiquement
public class PaiementService {

  private final PaiementRepository paiementRepository;
  private final EcheanceRepository echeanceRepository;
  private final RecuService recuService;

  public PaiementService(
      PaiementRepository paiementRepository,
      EcheanceRepository echeanceRepository,
      RecuService recuService) {
    this.paiementRepository = paiementRepository;
    this.echeanceRepository = echeanceRepository;
    this.recuService = recuService;
  }

  @Transactional(readOnly = true)
  public List<PaiementResponse> obtenirTous() {

    return paiementRepository.findAll().stream().map(this::toResponse).toList();
  }

  @Transactional(readOnly = true)
  public PaiementResponse obtenirParId(Long id) {

    return toResponse(trouverPaiement(id));
  }

  public PaiementResponse creer(PaiementRequest request) {

    Echeance echeance =
        echeanceRepository
            .findById(request.idEcheance())
            .orElseThrow(() -> new ResourceNotFoundException("Échéance", request.idEcheance()));

    verifierMontant(echeance, request.montant());

    Paiement paiement =
        Paiement.builder()
            .echeance(echeance)
            .montant(request.montant())
            .datePaiement(request.datePaiement() != null ? request.datePaiement() : LocalDate.now())
            .modePaiement(request.modePaiement())
            .statut(StatutPaiement.VALIDE)
            .build();

    Paiement paiementEnregistre = paiementRepository.save(paiement);

    mettreAJourStatutEcheance(echeance);

    recuService.creerPourPaiement(paiementEnregistre);

    return toResponse(paiementEnregistre);
  }

  public PaiementResponse modifier(Long id, PaiementRequest request) {

    Paiement paiement = trouverPaiement(id);

    if (paiement.getStatut() == StatutPaiement.ANNULE) {
      throw new IllegalArgumentException("Un paiement annulé ne peut pas être modifié");
    }

    Echeance ancienneEcheance = paiement.getEcheance();

    Echeance nouvelleEcheance =
        echeanceRepository
            .findById(request.idEcheance())
            .orElseThrow(() -> new ResourceNotFoundException("Échéance", request.idEcheance()));

    verifierMontant(nouvelleEcheance, request.montant(), id);

    paiement.setEcheance(nouvelleEcheance);
    paiement.setMontant(request.montant());

    if (request.datePaiement() != null) {
      paiement.setDatePaiement(request.datePaiement());
    }

    paiement.setModePaiement(request.modePaiement());

    Paiement paiementModifie = paiementRepository.save(paiement);

    mettreAJourStatutEcheance(ancienneEcheance);

    if (!ancienneEcheance.getIdEcheance().equals(nouvelleEcheance.getIdEcheance())) {

      mettreAJourStatutEcheance(nouvelleEcheance);
    }

    return toResponse(paiementModifie);
  }

  public void annuler(Long id) {

    Paiement paiement = trouverPaiement(id);

    if (paiement.getStatut() == StatutPaiement.ANNULE) {
      throw new IllegalArgumentException("Le paiement est déjà annulé");
    }

    paiement.setStatut(StatutPaiement.ANNULE);

    paiementRepository.save(paiement);

    mettreAJourStatutEcheance(paiement.getEcheance());
  }

  public void supprimer(Long id) {

    Paiement paiement = trouverPaiement(id);

    if (paiement.getStatut() == StatutPaiement.VALIDE) {
      throw new IllegalArgumentException("Un paiement validé doit être annulé avant suppression");
    }

    paiementRepository.delete(paiement);

    mettreAJourStatutEcheance(paiement.getEcheance());
  }

  @Transactional(readOnly = true)
  public List<PaiementResponse> obtenirParEcheance(Long idEcheance) {

    if (!echeanceRepository.existsById(idEcheance)) {
      throw new ResourceNotFoundException("Échéance", idEcheance);
    }

    return paiementRepository.findByEcheanceIdEcheance(idEcheance).stream()
        .map(this::toResponse)
        .toList();
  }

  private void mettreAJourStatutEcheance(Echeance echeance) {

    BigDecimal totalPaye =
        paiementRepository.findByEcheanceIdEcheance(echeance.getIdEcheance()).stream()
            .filter(p -> p.getStatut() == StatutPaiement.VALIDE)
            .map(Paiement::getMontant)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    if (totalPaye.compareTo(BigDecimal.ZERO) == 0) {

      echeance.setStatut(StatutEcheance.IMPAYEE);

    } else if (totalPaye.compareTo(echeance.getMontant()) >= 0) {

      echeance.setStatut(StatutEcheance.PAYEE);

    } else {

      echeance.setStatut(StatutEcheance.PARTIELLE);
    }

    echeanceRepository.save(echeance);
  }

  private void verifierMontant(Echeance echeance, BigDecimal montant) {
    verifierMontant(echeance, montant, null);
  }

  private void verifierMontant(Echeance echeance, BigDecimal montant, Long paiementIgnore) {

    BigDecimal dejaPaye =
        paiementRepository.findByEcheanceIdEcheance(echeance.getIdEcheance()).stream()
            .filter(
                p ->
                    p.getStatut() == StatutPaiement.VALIDE
                        && (paiementIgnore == null || !p.getIdPaiement().equals(paiementIgnore)))
            .map(Paiement::getMontant)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal nouveauTotal = dejaPaye.add(montant);

    if (nouveauTotal.compareTo(echeance.getMontant()) > 0) {

      throw new IllegalArgumentException(
          "Le montant total des paiements dépasse " + "le montant de l'échéance");
    }
  }

  private Paiement trouverPaiement(Long id) {

    return paiementRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
  }

  private PaiementResponse toResponse(Paiement paiement) {

    return new PaiementResponse(
        paiement.getIdPaiement(),
        paiement.getEcheance().getIdEcheance(),
        paiement.getMontant(),
        paiement.getDatePaiement(),
        paiement.getModePaiement(),
        paiement.getStatut());
  }
}
