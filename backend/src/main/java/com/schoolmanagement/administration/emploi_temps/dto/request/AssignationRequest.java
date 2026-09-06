package com.schoolmanagement.administration.emploi_temps.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignationRequest {
  @NotNull(message = "L'ID de la classe est obligatoire")
  private Long classeId;

  @NotNull(message = "L'ID de la matière est obligatoire")
  private Long matiereId;

  @NotNull(message = "L'ID de l'enseignant est obligatoire")
  private Long enseignantId;

  @NotNull(message = "Le volume horaire hebdomadaire est obligatoire")
  @Min(value = 1, message = "Le volume horaire doit être d'au moins 1 heure")
  private Integer heuresHebdomadaires;
}
