package com.schoolmanagement.comptabilite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recu")
public class Recu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRecu;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "idPaiement",
            nullable = false,
            unique = true
    )
    private Paiement paiement;

    @Column(nullable = false, unique = true, length = 50)
    private String numeroRecu;

    @Column(nullable = false)
    private LocalDate dateEmission;

    @PrePersist
    protected void onCreate() {
        if (dateEmission == null) {
            dateEmission = LocalDate.now();
        }
    }
}