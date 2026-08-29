package com.schoolmanagement.administration.emploi_temps.dto.response;

import lombok.Data;
import java.time.LocalTime;

@Data
public class EmploiDuTempsResponse {
    private Long id;
    private String nomClasse;
    private String nomMatiere;
    private String nomEnseignant;
    private String prenomEnseignant;
    private String jour;
    private LocalTime heureDebut;
    private LocalTime heureFin;
}