package com.schoolmanagement.administration.emploi_temps.entity;

import com.schoolmanagement.common.domain.EntieBase;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "matieres")
public class Matiere extends EntieBase {
    @Column(nullable = false, length = 50)
    private String nomMatiere;

    @Column(nullable = false)
    private Float coefficient;
    @Column(length = 20)
    private String code;
    @Column(length = 100)
    private String nom;
}