package com.schoolmanagement.administration.gestion_scolaire.controller;

import com.schoolmanagement.administration.gestion_scolaire.dto.request.InscriptionRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.InscriptionResponse;
import com.schoolmanagement.administration.gestion_scolaire.service.InscriptionService;
import jakarta.validation.Valid;
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
        InscriptionResponse response = inscriptionService.creerInscription(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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

    @PutMapping("/{id}")
    public ResponseEntity<InscriptionResponse> mettreAJourInscription(@PathVariable Long id, @Valid @RequestBody InscriptionRequest request) {
        InscriptionResponse response = inscriptionService.mettreAJourInscription(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerInscription(@PathVariable Long id) {
        inscriptionService.supprimerInscription(id);
        return ResponseEntity.noContent().build();
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
}