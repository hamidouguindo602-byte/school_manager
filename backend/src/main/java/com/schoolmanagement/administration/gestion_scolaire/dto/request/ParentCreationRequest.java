package com.schoolmanagement.administration.gestion_scolaire.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ParentCreationRequest {
  @NotBlank(message = "Le nom est obligatoire")
  @Size(max = 50)
  private String nom;

  @NotBlank(message = "Le prénom est obligatoire")
  @Size(max = 50)
  private String prenom;

  @NotBlank(message = "Le numéro de téléphone est obligatoire")
  @Size(max = 20)
  private String numeroTelephone;

  @Email(message = "Format d'email invalide")
  @Size(max = 100)
  private String email;

  @NotBlank(message = "Le mot de passe est obligatoire")
  private String motDePasse;
}
