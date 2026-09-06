package com.schoolmanagement.comptabilite.service;

import com.schoolmanagement.common.audit.JournaliserAutomatiquement;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.schoolmanagement.common.exception.ResourceNotFoundException;
import com.schoolmanagement.comptabilite.dto.response.RecuResponse;
import com.schoolmanagement.comptabilite.entity.Echeance;
import com.schoolmanagement.comptabilite.entity.Paiement;
import com.schoolmanagement.comptabilite.entity.Recu;
import com.schoolmanagement.comptabilite.entity.StatutPaiement;
import com.schoolmanagement.comptabilite.repository.PaiementRepository;
import com.schoolmanagement.comptabilite.repository.RecuRepository;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@JournaliserAutomatiquement
public class RecuService {

  private final RecuRepository recuRepository;
  private final PaiementRepository paiementRepository;

  public RecuService(RecuRepository recuRepository, PaiementRepository paiementRepository) {
    this.recuRepository = recuRepository;
    this.paiementRepository = paiementRepository;
  }

  @Transactional(readOnly = true)
  public List<RecuResponse> obtenirTous() {

    return recuRepository.findAll().stream().map(this::toResponse).toList();
  }

  @Transactional(readOnly = true)
  public RecuResponse obtenirParId(Long id) {

    return toResponse(trouverRecu(id));
  }

  @Transactional(readOnly = true)
  public RecuResponse obtenirParPaiement(Long idPaiement) {

    return recuRepository
        .findByPaiementIdPaiement(idPaiement)
        .map(this::toResponse)
        .orElseThrow(() -> new ResourceNotFoundException("Reçu du paiement", idPaiement));
  }

  public RecuResponse creerPourPaiement(Paiement paiement) {

    if (paiement.getStatut() != StatutPaiement.VALIDE) {

      throw new IllegalArgumentException(
          "Un reçu ne peut être créé " + "que pour un paiement valide");
    }

    if (recuRepository.existsByPaiementIdPaiement(paiement.getIdPaiement())) {

      return obtenirParPaiement(paiement.getIdPaiement());
    }

    Recu recu =
        Recu.builder()
            .paiement(paiement)
            .numeroRecu(genererNumeroRecu())
            .dateEmission(LocalDate.now())
            .build();

    return toResponse(recuRepository.save(recu));
  }

  private String genererNumeroRecu() {

    int annee = LocalDate.now().getYear();

    long prochainNumero = recuRepository.count() + 1;

    return String.format("REC-%d-%06d", annee, prochainNumero);
  }

  private Recu trouverRecu(Long id) {

    return recuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reçu", id));
  }

  private RecuResponse toResponse(Recu recu) {

    return new RecuResponse(
        recu.getIdRecu(),
        recu.getPaiement().getIdPaiement(),
        recu.getNumeroRecu(),
        recu.getDateEmission());
  }

  public byte[] genererPdf(Long idRecu) {

    Recu recu = trouverRecu(idRecu);

    Paiement paiement = recu.getPaiement();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    Document document = new Document();

    try {
      PdfWriter.getInstance(document, outputStream);

      document.open();

      document.add(new Paragraph("REÇU DE PAIEMENT"));

      document.add(new Paragraph(" "));

      document.add(new Paragraph("Numéro du reçu : " + recu.getNumeroRecu()));

      document.add(new Paragraph("Date d'émission : " + recu.getDateEmission()));

      document.add(new Paragraph(" "));

      document.add(new Paragraph("Paiement N° : " + paiement.getIdPaiement()));

      document.add(new Paragraph("Montant payé : " + paiement.getMontant() + " DH"));

      document.add(new Paragraph("Date du paiement : " + paiement.getDatePaiement()));

      document.add(new Paragraph("Mode de paiement : " + paiement.getModePaiement()));

      document.add(new Paragraph("Statut : " + paiement.getStatut()));

      document.add(new Paragraph(" "));

      Echeance echeance = paiement.getEcheance();

      document.add(new Paragraph("Échéance : " + echeance.getLibelle()));

      document.add(new Paragraph("Montant de l'échéance : " + echeance.getMontant() + " DH"));

      document.add(new Paragraph(" "));

      document.add(new Paragraph("Merci pour votre paiement."));

      document.close();

      return outputStream.toByteArray();

    } catch (DocumentException e) {

      throw new IllegalStateException("Erreur lors de la génération du reçu PDF", e);
    }
  }
}
