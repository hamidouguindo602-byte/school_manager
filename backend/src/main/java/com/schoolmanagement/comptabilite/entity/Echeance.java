package com.schoolmanagement.comptabilite.entity;

import com.schoolmanagement.administration.gestion_scolaire.entity.Inscription;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "echeance")
public class Echeance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idEcheance;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "idInscription", nullable = false)
  private Inscription inscription;

  @Column(nullable = false, length = 100)
  private String libelle;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal montant;

  @Column(nullable = false)
  private LocalDate dateLimite;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  @Builder.Default
  private StatutEcheance statut = StatutEcheance.IMPAYEE;
}
