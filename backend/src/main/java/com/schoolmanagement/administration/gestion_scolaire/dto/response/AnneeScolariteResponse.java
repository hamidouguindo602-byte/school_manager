package com.schoolmanagement.administration.gestion_scolaire.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AnneeScolariteResponse {
    private Long id;
    private String libelle;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statut;
}