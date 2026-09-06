package com.schoolmanagement.administration.absences.entity;

import com.schoolmanagement.administration.emploi_temps.entity.EmploiDuTemps;
import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.common.domain.EntieBase;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
    name = "absences",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_absence_eleve_edt_date",
          columnNames = {"eleve_id", "emploi_du_temps_id", "date_absence"})
    })
public class Absence extends EntieBase {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "eleve_id", nullable = false)
  private Utilisateur eleve;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "emploi_du_temps_id", nullable = false)
  private EmploiDuTemps emploiDuTemps;

  @Column(nullable = false)
  private LocalDate dateAbsence;

  @Builder.Default
  @Column(nullable = false)
  private Boolean justifiee = false;

  @Column(columnDefinition = "TEXT")
  private String motif;

  private LocalDate dateJustification;

  @Column(length = 500)
  private String documentPath;

  @Column(length = 255)
  private String documentName;

  @Column(length = 100)
  private String documentType;

  private Long documentSize;
}
