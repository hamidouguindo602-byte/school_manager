package com.schoolmanagement.administration.gestion_scolaire.entity;

import com.schoolmanagement.authentication.entity.Utilisateur;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "eleves")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Eleve extends Utilisateur {

  @Column(name = "date_naissance", nullable = false)
  private LocalDate dateNaissance;

  @Column(length = 255)
  private String adresse;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idParent")
  private Parent parent;

  @Builder.Default
  @OneToMany(mappedBy = "eleve", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Inscription> inscriptions = new ArrayList<>();
}
