package com.schoolmanagement.administration.emploi_temps.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClasseRequest {
  @NotBlank
  @Size(max = 50)
  private String nomClasse;

  @NotBlank
  @Size(max = 50)
  private String niveau;
}
