package com.schoolmanagement.administration.gestion_scolaire.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FormationResponse {
    private Long id;
    private String nomFormation;
    private String description;
    private Integer duree;
}