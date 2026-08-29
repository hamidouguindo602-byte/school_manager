package com.schoolmanagement.administration.gestion_scolaire.entity;

import com.schoolmanagement.common.domain.EntieBase;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.schoolmanagement.administration.emploi_temps.entity.Classe;

import java.time.LocalDate;

@Entity
@Table(name = "inscription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Inscription extends EntieBase {

    @Column(name = "date_inscription")
    private LocalDate dateInscription;

    @Column(nullable = false)
    private String statut; // 'en_attente', 'valide', 'annule'

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEleve", nullable = false)
    private Eleve eleve;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFormation", nullable = false)
    private Formation formation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idClasse", nullable = false)
    private Classe classe;
}