package com.schoolmanagement.administration.gestion_scolaire.dto.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EleveResponse {
  private Long id;
  private String nom;
  private String prenom;
  private String numeroTelephone;
  private String email;
  private String statut;
  private LocalDate dateNaissance;
  private String adresse;
  private Long idParent;
}
