package com.schoolmanagement.administration.gestion_scolaire.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParentResponse {
  private Long id;
  private String nom;
  private String prenom;
  private String numeroTelephone;
  private String email;
  private String statut;
}
