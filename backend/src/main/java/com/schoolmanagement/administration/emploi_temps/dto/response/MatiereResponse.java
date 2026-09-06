package com.schoolmanagement.administration.emploi_temps.dto.response;

import lombok.Data;

@Data
public class MatiereResponse {
  private Long id;
  private String nomMatiere;
  private Float coefficient;
  private String code;
  private String nom;
}
