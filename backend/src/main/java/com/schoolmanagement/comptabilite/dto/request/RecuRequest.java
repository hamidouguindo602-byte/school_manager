package com.schoolmanagement.comptabilite.dto.request;

import jakarta.validation.constraints.NotNull;

public record RecuRequest(@NotNull(message = "Le paiement est obligatoire") Long idPaiement) {}
