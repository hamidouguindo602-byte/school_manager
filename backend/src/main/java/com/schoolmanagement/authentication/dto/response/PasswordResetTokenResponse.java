package com.schoolmanagement.authentication.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetTokenResponse {
    private Long id;
    private String token;
    private LocalDateTime expiryDate;
    private boolean used;
    private Long userId;
}
