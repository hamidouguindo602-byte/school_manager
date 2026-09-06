package com.schoolmanagement.comptabilite.dto.response;

import com.schoolmanagement.comptabilite.entity.StatutPaiement;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PaiementResponse(
    Long idPaiement,
    Long idEcheance,
    BigDecimal montant,
    LocalDate datePaiement,
    String modePaiement,
    StatutPaiement statut) {}
