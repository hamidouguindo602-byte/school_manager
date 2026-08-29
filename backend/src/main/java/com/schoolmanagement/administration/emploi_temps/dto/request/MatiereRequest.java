package com.schoolmanagement.administration.emploi_temps.dto.request;

import lombok.Data;

@Data
public class MatiereRequest {
    private String nomMatiere;
    private Float coefficient;
    private String code;
    private String nom;
}