package com.schoolmanagement.comptabilite.dto.response;

import com.schoolmanagement.comptabilite.entity.StatutEcheance;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EcheanceResponse(

        Long idEcheance,

        Long idInscription,

        String libelle,

        BigDecimal montant,

        LocalDate dateLimite,

        StatutEcheance statut
) {
}