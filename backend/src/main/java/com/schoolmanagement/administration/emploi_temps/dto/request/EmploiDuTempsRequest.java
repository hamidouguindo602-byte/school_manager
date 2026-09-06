package com.schoolmanagement.administration.emploi_temps.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.Data;

@Data
public class EmploiDuTempsRequest {

  @NotNull(message = "L'ID de l'assignation est obligatoire")
  private Long assignationId;

  @NotNull(message = "Le jour est obligatoire")
  private String jour;

  @NotNull(message = "L'heure de début est obligatoire")
  private LocalTime heureDebut;

  @NotNull(message = "L'heure de fin est obligatoire")
  private LocalTime heureFin;

  @AssertTrue(message = "L'heure de fin doit être après l'heure de début")
  public boolean isHeureValide() {
    return heureDebut != null && heureFin != null && heureDebut.isBefore(heureFin);
  }
}
