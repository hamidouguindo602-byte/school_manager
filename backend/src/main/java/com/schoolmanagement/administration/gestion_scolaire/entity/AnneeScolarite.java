package com.schoolmanagement.administration.gestion_scolaire.entity;

import com.schoolmanagement.common.domain.EntieBase;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "annees_scolaires")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AnneeScolarite extends EntieBase {

    @Column(nullable = false, length = 50)
    private String libelle; // Ex: "2025-2026"

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Column(nullable = false, length = 20)
    private String statut; // Ex: "ACTIVE", "CLOTUREE"
}