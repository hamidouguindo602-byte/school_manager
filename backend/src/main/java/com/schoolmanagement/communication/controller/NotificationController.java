package com.schoolmanagement.communication.controller;

import com.schoolmanagement.communication.dto.Response.NotificationResponse;
import com.schoolmanagement.communication.dto.request.NotificationRequest;
import com.schoolmanagement.communication.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> creer(
            @Valid @RequestBody NotificationRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notificationService.creer(request));
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> consulterMesNotifications(
            @RequestParam Long idUtilisateur
    ) {

        return ResponseEntity.ok(
                notificationService.consulterMesNotifications(idUtilisateur)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> consulter(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                notificationService.consulter(id)
        );
    }

    @PatchMapping("/{id}/lire")
    public ResponseEntity<NotificationResponse> marquerCommeLue(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                notificationService.marquerCommeLue(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id
    ) {

        notificationService.supprimer(id);

        return ResponseEntity.noContent().build();
    }
}