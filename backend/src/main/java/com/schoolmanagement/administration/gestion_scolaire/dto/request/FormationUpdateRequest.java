package com.schoolmanagement.administration.gestion_scolaire.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FormationUpdateRequest {
    @Size(max = 100)
    private String nomFormation;

    private String description;

    @Positive(message = "La durée doit être supérieure à 0")
    private Integer duree;
}