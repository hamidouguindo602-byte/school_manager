package com.schoolmanagement.administration.gestion_scolaire.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EleveUpdateRequest {

    @Size(max = 50)
    private String nom;

    @Size(max = 50)
    private String prenom;

    @Size(max = 20)
    private String numeroTelephone;

    @Email(message = "Format d'email invalide")
    @Size(max = 100)
    private String email;

    private LocalDate dateNaissance;

    private String adresse;

    private Long idParent;
}