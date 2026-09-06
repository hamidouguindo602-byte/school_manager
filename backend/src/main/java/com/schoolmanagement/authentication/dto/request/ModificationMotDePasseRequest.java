package com.schoolmanagement.authentication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ModificationMotDePasseRequest(
    @NotBlank String ancienMotDePasse,
    @NotBlank
        @Size(min = 8, max = 30)
        @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,30}$",
            message =
                "Le mot de passe doit contenir une majuscule, une minuscule, un chiffre et un"
                    + " caractère spécial")
        String nouveauMotDePasse) {}
