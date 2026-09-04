package com.schoolmanagement.authentication.entity;

import com.schoolmanagement.common.domain.EntieBase;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "utilisateurs")
public class Utilisateur extends EntieBase {

@Column(nullable = false, length = 50)
private String nom;

@Column(nullable = false, length = 50)
private String prenom;

@Column(nullable = false, unique = true, length = 20)
private String numeroTelephone;

@Column(unique = true, length = 100)
private String email;

@Column(nullable = false, length = 255)
private String motDePasse;

@Enumerated(EnumType.STRING)
@Column(nullable = false, length = 20)
private StatutUtilisateur statut;

@Enumerated(EnumType.STRING)
@Column(nullable = false, length = 20)
private TypeRole typeRole;

@ManyToMany
@JoinTable(
name = "utilisateur_permission",
joinColumns = @JoinColumn(name = "utilisateur_id"),
inverseJoinColumns = @JoinColumn(name = "permission_id")
)
private Set<Permission> permissions = new HashSet<>();



}