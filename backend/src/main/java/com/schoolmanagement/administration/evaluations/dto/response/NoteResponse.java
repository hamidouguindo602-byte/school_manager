package com.schoolmanagement.administration.evaluations.dto.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoteResponse {
  private Long id;
  private Float valeur;
  private LocalDate dateSaisie;
  private Long idEvaluation;
  private Long idEleve;
}
