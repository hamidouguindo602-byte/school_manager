package com.schoolmanagement.administration.gestion_scolaire.dto.request;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Data;

@Data
public class AnneeScolariteUpdateRequest {
  @Size(max = 50)
  private String libelle;

  private LocalDate dateDebut;

  private LocalDate dateFin;

  @Size(max = 20)
  private String statut;
}
