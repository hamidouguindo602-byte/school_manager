package com.schoolmanagement.administration.evaluations.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NoteRequest {
  @NotNull(message = "L'ID de l'évaluation est obligatoire")
  private Long idEvaluation;

  @NotNull(message = "L'ID de l'élève est obligatoire")
  private Long idEleve;

  @NotNull(message = "La valeur de la note est obligatoire")
  @Min(value = 0, message = "La note minimale est 0")
  @Max(value = 20, message = "La note maximale est 20")
  private Float valeur;
}
