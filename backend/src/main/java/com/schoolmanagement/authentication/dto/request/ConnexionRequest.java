package com.schoolmanagement.authentication.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ConnexionRequest(
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
        String motDePasse


                                /*{
        "numeroTelephone": "+2250700000000",
        "email": null,
        "motDePasse": "Test123!"
}*/
) {
                @AssertTrue(message = "Le numéro de téléphone ou l'email est obligatoire")
                public boolean hasIdentifiant() {
                                return (numeroTelephone != null && !numeroTelephone.isBlank())
                                                                || (email != null && !email.isBlank());
                }
}