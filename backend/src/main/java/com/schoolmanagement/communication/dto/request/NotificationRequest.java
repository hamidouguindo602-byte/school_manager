package com.schoolmanagement.communication.dto.request;

import com.schoolmanagement.communication.entity.TypeNotification;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationRequest(
    @NotNull(message = "L'utilisateur destinataire est obligatoire") Long idUtilisateur,
    @NotBlank(message = "Le titre est obligatoire") String titre,
    @NotBlank(message = "Le message est obligatoire") String message,
    @NotNull(message = "Le type de notification est obligatoire") TypeNotification type) {}
