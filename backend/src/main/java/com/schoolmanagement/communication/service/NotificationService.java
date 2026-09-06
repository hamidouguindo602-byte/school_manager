package com.schoolmanagement.communication.service;

import com.schoolmanagement.common.audit.JournaliserAutomatiquement;

import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.authentication.repository.UtilisateurRepository;
import com.schoolmanagement.common.exception.ResourceNotFoundException;
import com.schoolmanagement.communication.dto.Response.NotificationResponse;
import com.schoolmanagement.communication.dto.request.NotificationRequest;
import com.schoolmanagement.communication.entity.Notification;
import com.schoolmanagement.communication.entity.StatutNotification;
import com.schoolmanagement.communication.repository.NotificationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@JournaliserAutomatiquement
public class NotificationService {

  private final NotificationRepository notificationRepository;
  private final UtilisateurRepository utilisateurRepository;

  @Transactional
  public NotificationResponse creer(NotificationRequest request) {

    Utilisateur utilisateur =
        utilisateurRepository
            .findById(request.idUtilisateur())
            .orElseThrow(
                () -> new ResourceNotFoundException("Utilisateur", request.idUtilisateur()));

    Notification notification =
        Notification.builder()
            .utilisateur(utilisateur)
            .titre(request.titre())
            .message(request.message())
            .type(request.type())
            .statut(StatutNotification.ENVOYEE)
            .build();

    Notification notificationEnregistree = notificationRepository.save(notification);

    return convertirEnResponse(notificationEnregistree);
  }

  @Transactional(readOnly = true)
  public List<NotificationResponse> consulterMesNotifications(Long idUtilisateur) {

    return notificationRepository.findByUtilisateur_IdOrderByDateEnvoiDesc(idUtilisateur).stream()
        .map(this::convertirEnResponse)
        .toList();
  }

  @Transactional(readOnly = true)
  public NotificationResponse consulter(Long idNotification) {

    Notification notification =
        notificationRepository
            .findById(idNotification)
            .orElseThrow(() -> new ResourceNotFoundException("Notification", idNotification));
    verifierProprietaireOuAdmin(notification);

    return convertirEnResponse(notification);
  }

  @Transactional
  public NotificationResponse marquerCommeLue(Long idNotification) {

    Notification notification =
        notificationRepository
            .findById(idNotification)
            .orElseThrow(() -> new ResourceNotFoundException("Notification", idNotification));
    verifierProprietaireOuAdmin(notification);

    notification.setStatut(StatutNotification.LUE);

    return convertirEnResponse(notificationRepository.save(notification));
  }

  @Transactional
  public void supprimer(Long idNotification) {
    Notification notification =
        notificationRepository
            .findById(idNotification)
            .orElseThrow(() -> new ResourceNotFoundException("Notification", idNotification));
    verifierProprietaireOuAdmin(notification);
    notificationRepository.delete(notification);
  }

  private void verifierProprietaireOuAdmin(Notification notification) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    boolean admin =
        authentication != null
            && authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    boolean proprietaire =
        authentication != null
            && authentication.getPrincipal() instanceof Number principal
            && principal.longValue() == notification.getUtilisateur().getId();
    if (!admin && !proprietaire) {
      throw new AccessDeniedException("Acces refuse");
    }
  }

  private NotificationResponse convertirEnResponse(Notification notification) {

    return new NotificationResponse(
        notification.getIdNotification(),
        notification.getUtilisateur().getId(),
        notification.getTitre(),
        notification.getMessage(),
        notification.getDateEnvoi(),
        notification.getStatut(),
        notification.getType());
  }
}
