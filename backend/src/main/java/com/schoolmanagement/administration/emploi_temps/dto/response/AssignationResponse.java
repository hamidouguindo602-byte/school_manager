package com.schoolmanagement.administration.emploi_temps.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignationResponse {
  private Long id;
  private Long classeId;
  private String nomClasse;
  private Long matiereId;
  private String nomMatiere;
  private Long enseignantId;
  private String nomEnseignant;
  private Integer heuresHebdomadaires;
}
