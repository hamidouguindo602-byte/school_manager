package com.schoolmanagement.administration.emploi_temps.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MatiereRequest {
  @NotBlank
  @Size(max = 100)
  private String nomMatiere;

  @NotNull @Positive private Float coefficient;

  @NotBlank
  @Size(max = 30)
  private String code;

  @Size(max = 100)
  private String nom;
}
