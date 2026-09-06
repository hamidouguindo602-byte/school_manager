package com.schoolmanagement.administration.gestion_scolaire.dto.request;

import com.schoolmanagement.administration.gestion_scolaire.entity.StatusInscription;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class InscriptionRequest {

  @NotNull(message = "L'ID de l'élève est obligatoire")
  private Long idEleve;

  @NotNull(message = "L'ID de la formation est obligatoire")
  private Long idFormation;

  @NotNull(message = "L'ID de la classe est obligatoire")
  private Long idClasse;

  private LocalDate dateInscription;

  @NotNull(message = "Le statut est obligatoire et doit être parmi : en_attente, valide, annule")
  private StatusInscription statut; // Exige que l'utilisateur passe l'un des trois mots
}
