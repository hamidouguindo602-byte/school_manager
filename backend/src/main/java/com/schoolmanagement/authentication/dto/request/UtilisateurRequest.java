package com.schoolmanagement.authentication.dto.request;

import com.schoolmanagement.authentication.entity.StatutUtilisateur;
import com.schoolmanagement.authentication.entity.TypeRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UtilisateurRequest(

        @NotBlank
        @Size(max = 20)
        String numeroTelephone,

        @Email
        @Size(max = 100)
        String email,

        @NotBlank
        @Size(max = 255)
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