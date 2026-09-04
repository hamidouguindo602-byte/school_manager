package com.schoolmanagement.administration.gestion_scolaire.entity;

import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.common.domain.EntieBase;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Parent extends EntieBase {

@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "idUtilisateur", nullable = false, unique = true)
private Utilisateur utilisateur;

@Builder.Default
@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Eleve> eleves = new ArrayList<>();
}