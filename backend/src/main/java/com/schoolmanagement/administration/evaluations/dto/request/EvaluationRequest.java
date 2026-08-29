package com.schoolmanagement.administration.evaluations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EvaluationRequest {
    @NotNull(message = "L'ID de la matière est obligatoire")
    private Long idMatiere;

    @NotNull(message = "L'ID de l'enseignant est obligatoire")
    private Long idEnseignant;

    @NotNull(message = "L'ID de la classe est obligatoire")
    private Long idClasse;

    @NotNull(message = "La date d'évaluation est obligatoire")
    private LocalDate dateEvaluation;

    @NotBlank(message = "Le type d'évaluation est obligatoire")
    private String type;
}