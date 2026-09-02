package com.schoolmanagement.authentication.dto.request;

import com.schoolmanagement.authentication.entity.StatutUtilisateur;
import com.schoolmanagement.authentication.entity.TypeRole;
import jakarta.validation.constraints.*;

import java.util.Set;

public record UtilisateurRequest(

        @NotBlank
        @Size(max = 20)
        String numeroTelephone,

        @Email
        @Size(max = 100)
        String email,

        @NotBlank
        @Size(min = 8, max = 30)
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,30}$",
                message = "Le mot de passe doit contenir une majuscule, une minuscule, un chiffre et un caractère spécial"
        )
        String motDePasse,

        @NotBlank
        @Size(max = 50)
        String nom,

        @NotBlank
        @Size(max = 50)
        String prenom,

        @NotNull
        StatutUtilisateur statut,

        @NotNull
        TypeRole typeRole,
        Set<Long> permissionIds

) {
}