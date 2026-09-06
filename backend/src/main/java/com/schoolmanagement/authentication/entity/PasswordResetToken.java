package com.schoolmanagement.authentication.entity;

import com.schoolmanagement.common.domain.EntieBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken extends EntieBase {

  @Column(nullable = false, unique = true, length = 100)
  private String token;

  @Column(nullable = false)
  private LocalDateTime expiryDate;

  @Column(nullable = false)
  @Builder.Default
  private boolean used = false;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private Utilisateur user;
}
