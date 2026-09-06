package com.schoolmanagement.comptabilite.entity;

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
@Table(name = "paiement")
public class Paiement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idPaiement;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "idEcheance", nullable = false)
  private Echeance echeance;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal montant;

  @Column(nullable = false)
  private LocalDate datePaiement;

  @Column(nullable = false, length = 50)
  private String modePaiement;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  @Builder.Default
  private StatutPaiement statut = StatutPaiement.VALIDE;

  @PrePersist
  protected void onCreate() {
    if (datePaiement == null) {
      datePaiement = LocalDate.now();
    }

    if (statut == null) {
      statut = StatutPaiement.VALIDE;
    }
  }
}
