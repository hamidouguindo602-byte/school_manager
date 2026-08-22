package com.schoolmanagement.authentication.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetTokenRequest {
    private String token;
    private LocalDateTime expiryDate;
    private boolean used;
    private Long userId;
}
