package com.schoolmanagement.communication.controller;

import com.schoolmanagement.communication.dto.Response.NotificationResponse;
import com.schoolmanagement.communication.dto.request.NotificationRequest;
import com.schoolmanagement.communication.service.NotificationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN') or hasAuthority('NOTIFICATION_GERER')")
  public ResponseEntity<NotificationResponse> creer(
      @Valid @RequestBody NotificationRequest request) {

    return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.creer(request));
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN') or hasAuthority('NOTIFICATION_CONSULTER')")
  public ResponseEntity<List<NotificationResponse>> consulterMesNotifications(
      @RequestParam(required = false) Long idUtilisateur) {

    Long cible = idUtilisateur;
    if (cible == null) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication == null || !(authentication.getPrincipal() instanceof Number principal)) {
        throw new IllegalArgumentException("Utilisateur connecté introuvable");
      }
      cible = principal.longValue();
    }

    return ResponseEntity.ok(notificationService.consulterMesNotifications(cible));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') or hasAuthority('NOTIFICATION_CONSULTER')")
  public ResponseEntity<NotificationResponse> consulter(@PathVariable Long id) {

    return ResponseEntity.ok(notificationService.consulter(id));
  }

  @PatchMapping("/{id}/lire")
  @PreAuthorize("hasRole('ADMIN') or hasAuthority('NOTIFICATION_CONSULTER')")
  public ResponseEntity<NotificationResponse> marquerCommeLue(@PathVariable Long id) {

    return ResponseEntity.ok(notificationService.marquerCommeLue(id));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') or hasAuthority('NOTIFICATION_GERER')")
  public ResponseEntity<Void> supprimer(@PathVariable Long id) {

    notificationService.supprimer(id);

    return ResponseEntity.noContent().build();
  }
}
