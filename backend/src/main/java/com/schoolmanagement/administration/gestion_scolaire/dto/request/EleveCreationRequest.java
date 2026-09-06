package com.schoolmanagement.administration.gestion_scolaire.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Data;

@Data
public class EleveCreationRequest {

  @NotBlank(message = "Le nom est obligatoire")
  @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
  private String nom;

  @NotBlank(message = "Le prénom est obligatoire")
  @Size(max = 50, message = "Le prénom ne doit pas dépasser 50 caractères")
  private String prenom;

  @NotBlank(message = "Le numéro de téléphone est obligatoire")
  @Size(max = 20, message = "Le numéro de téléphone ne doit pas dépasser 20 caractères")
  private String numeroTelephone;

  @Email(message = "Format d'email invalide")
  @Size(max = 100)
  private String email;

  @NotBlank(message = "Le mot de passe est obligatoire")
  private String motDePasse;

  @NotNull(message = "La date de naissance est obligatoire")
  private LocalDate dateNaissance;

  private String adresse;

  private Long idParent;
}
