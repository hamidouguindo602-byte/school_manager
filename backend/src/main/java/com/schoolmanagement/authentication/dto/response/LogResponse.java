package com.schoolmanagement.authentication.dto.response;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogResponse {

  private Long id;

  private String action;

  private String description;

  private String ressource;

  private String resultat;

  private LocalDateTime dateAction;

  private Long utilisateurId;
}
