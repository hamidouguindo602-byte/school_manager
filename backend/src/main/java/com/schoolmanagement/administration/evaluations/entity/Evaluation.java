package com.schoolmanagement.administration.evaluations.entity;

import com.schoolmanagement.administration.emploi_temps.entity.Classe;
import com.schoolmanagement.administration.emploi_temps.entity.Matiere;
import com.schoolmanagement.administration.gestion_scolaire.entity.Enseignant;
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
@Table(name = "evaluation")
public class Evaluation extends EntieBase {

  @Column(name = "date_evaluation", nullable = false)
  private LocalDate dateEvaluation;

  @Column(nullable = false, length = 50)
  private String type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_matiere", nullable = false)
  private Matiere matiere;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_enseignant", nullable = false)
  private Enseignant enseignant;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_classe", nullable = false)
  private Classe classe;
}
