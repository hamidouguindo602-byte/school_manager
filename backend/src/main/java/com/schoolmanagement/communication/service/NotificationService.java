package com.schoolmanagement.communication.service;

import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.authentication.repository.UtilisateurRepository;
import com.schoolmanagement.communication.dto.Response.NotificationResponse;
import com.schoolmanagement.communication.dto.request.NotificationRequest;
import com.schoolmanagement.communication.entity.Notification;
import com.schoolmanagement.communication.entity.StatutNotification;
import com.schoolmanagement.communication.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Transactional
    public NotificationResponse creer(NotificationRequest request) {

        Utilisateur utilisateur = utilisateurRepository
                .findById(request.idUtilisateur())
                .orElseThrow(() ->
                        new RuntimeException("Utilisateur introuvable")
                );

        Notification notification = Notification.builder()
                .utilisateur(utilisateur)
                .titre(request.titre())
                .message(request.message())
                .type(request.type())
                .statut(StatutNotification.ENVOYEE)
                .build();

        Notification notificationEnregistree =
                notificationRepository.save(notification);

        return convertirEnResponse(notificationEnregistree);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> consulterMesNotifications(
            Long idUtilisateur
    ) {

        return notificationRepository
                .findByUtilisateur_IdOrderByDateEnvoiDesc(idUtilisateur)
                .stream()
                .map(this::convertirEnResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public NotificationResponse consulter(Long idNotification) {

        Notification notification = notificationRepository
                .findById(idNotification)
                .orElseThrow(() ->
                        new RuntimeException("Notification introuvable")
                );

        return convertirEnResponse(notification);
    }

    @Transactional
    public NotificationResponse marquerCommeLue(Long idNotification) {

        Notification notification = notificationRepository
                .findById(idNotification)
                .orElseThrow(() ->
                        new RuntimeException("Notification introuvable")
                );

        notification.setStatut(StatutNotification.LUE);

        return convertirEnResponse(
                notificationRepository.save(notification)
        );
    }

    @Transactional
    public void supprimer(Long idNotification) {

        if (!notificationRepository.existsById(idNotification)) {
            throw new RuntimeException("Notification introuvable");
        }

        notificationRepository.deleteById(idNotification);
    }

    private NotificationResponse convertirEnResponse(
            Notification notification
    ) {

        return new NotificationResponse(
                notification.getIdNotification(),
                notification.getUtilisateur().getId(),
                notification.getTitre(),
                notification.getMessage(),
                notification.getDateEnvoi(),
                notification.getStatut(),
                notification.getType()
        );
    }
}