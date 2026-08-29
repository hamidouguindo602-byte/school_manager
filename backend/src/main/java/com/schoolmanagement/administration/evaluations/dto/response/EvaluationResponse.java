package com.schoolmanagement.administration.evaluations.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EvaluationResponse {
    private Long id;
    private LocalDate dateEvaluation;
    private String type;
    private Long idMatiere;
    private String nomMatiere;
    private Long idEnseignant;
    private Long idClasse;
    private String nomClasse;
}