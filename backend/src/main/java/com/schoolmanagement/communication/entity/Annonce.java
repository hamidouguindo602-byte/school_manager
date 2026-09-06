package com.schoolmanagement.communication.entity;

import com.schoolmanagement.authentication.entity.Utilisateur;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "annonces")
public class Annonce {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idAnnonce;

  @NotBlank
  @Column(length = 100, nullable = false)
  private String titre;

  @Column(nullable = true)
  private String contenu;

  @CreatedDate
  @Column(nullable = false)
  private LocalDate datePublication;

  @Column(nullable = true)
  private String nomFichier;

  @Lob
  @Column(name = "fichier_pdf", columnDefinition = "LONGBLOB")
  private byte[] fichierPdf;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idAuteur", nullable = false)
  private Utilisateur auteur;
}
