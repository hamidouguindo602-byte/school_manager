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
@Table(name = "classes")
public class Classe extends EntieBase {
    @Column(nullable = false, length = 50)
    private String nomClasse;

    @Column(nullable = false, length = 50)
    private String niveau;
}