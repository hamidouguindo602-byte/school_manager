package com.schoolmanagement.administration.gestion_scolaire.entity;

import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.common.domain.EntieBase;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "eleves")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Eleve extends EntieBase { // Hérite de EntieBase, pas de Utilisateur

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(length = 255)
    private String adresse;

    // Lien 1 à 1 vers la table utilisateurs inchangée
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUtilisateur", nullable = false, unique = true)
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idParent")
    private Parent parent;

    @Builder.Default
    @OneToMany(mappedBy = "eleve", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscription> inscriptions = new ArrayList();
}