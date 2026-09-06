package com.schoolmanagement.authentication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogRequest {

  @NotBlank
  @Size(max = 100)
  private String action;

  @Size(max = 500)
  private String description;

  @Size(max = 100)
  private String ressource;

  @Size(max = 20)
  private String resultat;

  private LocalDateTime dateAction;

  @NotNull private Long utilisateurId;
}
