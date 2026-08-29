package com.schoolmanagement.administration.gestion_scolaire.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ParentUpdateRequest {
    @Size(max = 50)
    private String nom;

    @Size(max = 50)
    private String prenom;

    @Size(max = 20)
    private String numeroTelephone;

    @Email(message = "Format d'email invalide")
    @Size(max = 100)
    private String email;
}