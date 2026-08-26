package com.schoolmanagement.administration.gestion_scolaire.controller;

import com.schoolmanagement.administration.gestion_scolaire.dto.request.InscriptionRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.InscriptionResponse;
import com.schoolmanagement.administration.gestion_scolaire.service.InscriptionService;
import jakarta.validation.Valid; // Import important pour la validation
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
@RequiredArgsConstructor
public class InscriptionController {

    private final InscriptionService inscriptionService;

    @PostMapping
    public ResponseEntity<InscriptionResponse> creerInscription(@Valid @RequestBody InscriptionRequest request) {
        // @Valid force la vérification des contraintes du DTO (dont le statut)
        InscriptionResponse response = inscriptionService.creerInscription(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/valider")
    public ResponseEntity<InscriptionResponse> validerInscription(@PathVariable Long id) {
        InscriptionResponse response = inscriptionService.validerInscription(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/annuler")
    public ResponseEntity<InscriptionResponse> annulerInscription(@PathVariable Long id) {
        InscriptionResponse response = inscriptionService.annulerInscription(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<InscriptionResponse>> listerInscriptions() {
        List<InscriptionResponse> list = inscriptionService.listerToutesLesInscriptions();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscriptionResponse> recupererInscriptionParId(@PathVariable Long id) {
        InscriptionResponse response = inscriptionService.recupererParId(id);
        return ResponseEntity.ok(response);
    }
}