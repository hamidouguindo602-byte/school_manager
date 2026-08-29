package com.schoolmanagement.administration.gestion_scolaire.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AnneeScolariteCreationRequest {
    @NotBlank(message = "Le libellé est obligatoire")
    @Size(max = 50)
    private String libelle;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    @NotBlank(message = "Le statut est obligatoire")
    @Size(max = 20)
    private String statut;
}