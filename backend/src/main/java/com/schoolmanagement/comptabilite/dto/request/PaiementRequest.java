package com.schoolmanagement.comptabilite.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PaiementRequest(
    @NotNull(message = "L'échéance est obligatoire") Long idEcheance,
    @NotNull(message = "Le montant est obligatoire")
        @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0")
        BigDecimal montant,
    LocalDate datePaiement,
    @NotBlank(message = "Le mode de paiement est obligatoire") @Size(max = 50)
        String modePaiement) {}
