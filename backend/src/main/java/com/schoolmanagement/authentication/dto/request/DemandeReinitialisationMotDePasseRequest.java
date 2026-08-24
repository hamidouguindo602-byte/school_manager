package com.schoolmanagement.authentication.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DemandeReinitialisationMotDePasseRequest(
        @NotBlank
        @Email
        @Size(max = 100)
        String email
) {
}