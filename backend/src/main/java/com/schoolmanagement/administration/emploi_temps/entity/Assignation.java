package com.schoolmanagement.administration.emploi_temps.entity;

import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.common.domain.EntieBase;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
    name = "assignations",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = {"classe_id", "matiere_id", "enseignant_id"})
    })
public class Assignation extends EntieBase {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "classe_id", nullable = false)
  private Classe classe;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "matiere_id", nullable = false)
  private Matiere matiere;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "enseignant_id", nullable = false)
  private Utilisateur enseignant;

  @Column(nullable = false)
  private Integer heuresHebdomadaires; // Volume horaire prévu par semaine (ex: 4 heures)
}
