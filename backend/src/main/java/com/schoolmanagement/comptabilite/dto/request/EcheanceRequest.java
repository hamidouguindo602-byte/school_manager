package com.schoolmanagement.comptabilite.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EcheanceRequest(

        @NotNull(message = "L'inscription est obligatoire")
        Long idInscription,

        @NotBlank(message = "Le libellé est obligatoire")
        @Size(max = 100, message = "Le libellé ne doit pas dépasser 100 caractères")
        String libelle,

        @NotNull(message = "Le montant est obligatoire")
        @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0")
        BigDecimal montant,

        @NotNull(message = "La date limite est obligatoire")
        LocalDate dateLimite
) {
}