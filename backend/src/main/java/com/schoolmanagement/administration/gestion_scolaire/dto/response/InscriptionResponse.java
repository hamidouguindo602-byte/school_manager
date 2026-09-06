package com.schoolmanagement.administration.gestion_scolaire.dto.response;

import com.schoolmanagement.administration.gestion_scolaire.entity.StatusInscription;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InscriptionResponse {
  private Long idInscription;
  private Long idEleve;
  private Long idFormation;
  private Long idClasse;
  private LocalDate dateInscription;
  private StatusInscription statut; // Type Enum
}
