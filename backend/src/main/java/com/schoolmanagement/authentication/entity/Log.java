package com.schoolmanagement.authentication.entity;

import com.schoolmanagement.common.domain.EntieBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "journalisation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log extends EntieBase {

  @Column(nullable = false, length = 100)
  private String action;

  @Column(nullable = false)
  private LocalDateTime dateAction;

  @Column(length = 500)
  private String description;

  @Column(length = 100)
  private String ressource;

  @Column(length = 20)
  private String resultat;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "utilisateur_id")
  private Utilisateur utilisateur;
}
