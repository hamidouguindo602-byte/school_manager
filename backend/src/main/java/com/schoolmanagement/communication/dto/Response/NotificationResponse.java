package com.schoolmanagement.communication.dto.Response;

import com.schoolmanagement.communication.entity.StatutNotification;
import com.schoolmanagement.communication.entity.TypeNotification;

import java.time.LocalDateTime;

public record NotificationResponse(

        Long idNotification,
        Long idUtilisateur,
        String titre,
        String message,
        LocalDateTime dateEnvoi,
        StatutNotification statut,
        TypeNotification type
) {
}