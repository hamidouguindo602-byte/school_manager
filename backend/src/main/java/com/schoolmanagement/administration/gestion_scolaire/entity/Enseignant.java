package com.schoolmanagement.administration.gestion_scolaire.entity;

import com.schoolmanagement.authentication.entity.Utilisateur;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "enseignants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Enseignant extends Utilisateur {

    @Column(nullable = false, length = 100)
    private String specialite;

}