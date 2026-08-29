package com.schoolmanagement.administration.gestion_scolaire.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FormationCreationRequest {
    @NotBlank(message = "Le nom de la formation est obligatoire")
    @Size(max = 100)
    private String nomFormation;

    private String description;

    @NotNull(message = "La durée est obligatoire")
    @Positive(message = "La durée doit être supérieure à 0")
    private Integer duree;
}