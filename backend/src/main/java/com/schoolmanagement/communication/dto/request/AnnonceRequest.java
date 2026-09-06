package com.schoolmanagement.communication.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record AnnonceRequest(
    @NotBlank(message = "Le titre est obligatoire") String titre,
    String contenu,
    @Schema(type = "string", format = "binary", description = "Fichier PDF de l'annonce")
        MultipartFile fichier) {}
