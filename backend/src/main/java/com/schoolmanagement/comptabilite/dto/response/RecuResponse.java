package com.schoolmanagement.comptabilite.dto.response;

import java.time.LocalDate;

public record RecuResponse(
        Long idRecu,
        Long idPaiement,
        String numeroRecu,
        LocalDate dateEmission
) {
}