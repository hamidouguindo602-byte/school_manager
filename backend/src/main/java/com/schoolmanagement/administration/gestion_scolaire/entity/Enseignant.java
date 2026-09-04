package com.schoolmanagement.administration.gestion_scolaire.entity;

import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.common.domain.EntieBase;
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
public class Enseignant extends EntieBase {

@Column(nullable = false, length = 100)
private String specialite;

@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "idUtilisateur", nullable = false, unique = true)
private Utilisateur utilisateur;
}