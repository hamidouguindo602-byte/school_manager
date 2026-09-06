package com.schoolmanagement.administration.evaluations.entity;

import com.schoolmanagement.administration.gestion_scolaire.entity.Eleve;
import com.schoolmanagement.common.domain.EntieBase;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(
    name = "note",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"id_evaluation", "id_eleve"})})
public class Note extends EntieBase {

  @Column(nullable = false)
  private Float valeur;

  @Column(name = "date_saisie", nullable = false)
  private LocalDate dateSaisie;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_evaluation", nullable = false)
  private Evaluation evaluation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_eleve", nullable = false)
  private Eleve eleve;
}
