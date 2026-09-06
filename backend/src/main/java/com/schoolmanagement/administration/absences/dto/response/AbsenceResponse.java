package com.schoolmanagement.administration.absences.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AbsenceResponse {
  private Long id;
  private Long eleveId;
  private String nomEleve;
  private String prenomEleve;

  // Infos contexte (via emploiDuTemps → assignation)
  private Long classeId;
  private String nomClasse;
  private Long matiereId;
  private String nomMatiere;
  private Long enseignantId;
  private String nomEnseignant;
  private String prenomEnseignant;

  // Infos créneau horaire
  private String jour; // LUNDI, MARDI...
  private LocalTime heureDebut; // 08:00
  private LocalTime heureFin; // 10:00

  // Absence
  private LocalDate dateAbsence;
  private Boolean justifiee;
  private String motif;
  private LocalDate dateJustification;

  // Champs du document mis à jour (alignés avec l'entité)
  private String documentPath;
  private String documentName;
  private String documentType;
  private Long documentSize;
}
