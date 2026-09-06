package com.schoolmanagement.administration.evaluations.dto.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

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
