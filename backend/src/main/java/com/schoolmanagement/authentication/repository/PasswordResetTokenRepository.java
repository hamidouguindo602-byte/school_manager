package com.schoolmanagement.authentication.repository;

import com.schoolmanagement.authentication.entity.PasswordResetToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
  @EntityGraph(attributePaths = "user")
  Optional<PasswordResetToken> findByToken(String token);
}
