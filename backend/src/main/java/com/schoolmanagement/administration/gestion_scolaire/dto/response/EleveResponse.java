package com.schoolmanagement.administration.gestion_scolaire.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

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