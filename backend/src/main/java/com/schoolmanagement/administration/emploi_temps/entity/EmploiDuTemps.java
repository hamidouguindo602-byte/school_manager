package com.schoolmanagement.administration.emploi_temps.entity;

import com.schoolmanagement.common.domain.EntieBase;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "emplois_du_temps")
public class EmploiDuTemps extends EntieBase {

    // On remplace les 3 liaisons séparées par l'assignation globale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignation_id", nullable = false)
    private Assignation assignation;

    @Column(nullable = false, length = 20)
    private String jour; // Ex: "LUNDI"

    @Column(nullable = false)
    private LocalTime heureDebut;

    @Column(nullable = false)
    private LocalTime heureFin;
}