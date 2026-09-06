package com.schoolmanagement.authentication.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetTokenResponse {
  private Long id;
  @JsonIgnore private String token;
  private LocalDateTime expiryDate;
  private boolean used;
  private Long userId;
}
