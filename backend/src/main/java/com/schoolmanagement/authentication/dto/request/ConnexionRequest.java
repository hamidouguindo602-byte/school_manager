package com.schoolmanagement.authentication.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public record ConnexionRequest(
        @NotBlank(message = "L'identifiant (email ou numéro de téléphone) est obligatoire")
        @Size(max = 100, message = "L'identifiant ne doit pas dépasser 100 caractères")
        String identifiant,

        @NotBlank
        @Size(min = 8, max = 30)
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,30}$",
                message = "Le mot de passe doit contenir une majuscule, une minuscule, un chiffre et un caractère spécial"
        )
        String motDePasse

        /*{
        "identifiant": "0612345678",
        "motDePasse": "Test123!"
}
ou
{
        "identifiant": "amadou@gmail.com",
        "motDePasse": "Test123!"
}*/
) {
}