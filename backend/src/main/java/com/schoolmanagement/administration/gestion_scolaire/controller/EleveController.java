package com.schoolmanagement.administration.gestion_scolaire.controller;

import com.schoolmanagement.administration.gestion_scolaire.dto.request.EleveCreationRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.EleveUpdateRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.EleveResponse;
// Importez vos DTOs de réponse additionnels si nécessaire (ex: InscriptionResponse, AbsenceResponse, NoteResponse)
import com.schoolmanagement.administration.gestion_scolaire.service.EleveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eleves")
@RequiredArgsConstructor
public class EleveController {

    private final EleveService eleveService;

    /**
     * Créer un nouvel élève
     */
    @PostMapping
    public ResponseEntity<EleveResponse> creerEleve(@Valid @RequestBody EleveCreationRequest request) {
        EleveResponse nouveauEleve = eleveService.creerEleve(request);
        return new ResponseEntity<>(nouveauEleve, HttpStatus.CREATED);
    }

    /**
     * Récupérer un élève par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EleveResponse> obtenirEleveParId(@PathVariable Long id) {
        EleveResponse eleve = eleveService.obtenirEleveParId(id);
        return ResponseEntity.ok(eleve);
    }

    /**
     * Récupérer la liste de tous les élèves
     */
    @GetMapping
    public ResponseEntity<List<EleveResponse>> listerTousLesEleves() {
        List<EleveResponse> eleves = eleveService.listerTousLesEleves();
        return ResponseEntity.ok(eleves);
    }

    /**
     * Mettre à jour un élève existant
     */
    @PutMapping("/{id}")
    public ResponseEntity<EleveResponse> mettreAJourEleve(
            @PathVariable Long id, 
            @Valid @RequestBody EleveUpdateRequest request) {
        EleveResponse eleveMisAJour = eleveService.mettreAJourEleve(id, request);
        return ResponseEntity.ok(eleveMisAJour);
    }

    /**
     * Supprimer un élève
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerEleve(@PathVariable Long id) {
        eleveService.supprimerEleve(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Récupérer les inscriptions d'un élève
     */
    @GetMapping("/{id}/inscriptions")
    public ResponseEntity<List<Object>> obtenirInscriptionsParEleve(@PathVariable Long id) {
        // Remplacez 'Object' par votre 'InscriptionResponse' lorsque le service sera prêt
        return ResponseEntity.ok(eleveService.obtenirInscriptionsParEleve(id));
    }

    /**
     * Récupérer les absences d'un élève
     */
    @GetMapping("/{id}/absences")
    public ResponseEntity<List<Object>> obtenirAbsencesParEleve(@PathVariable Long id) {
        // Remplacez 'Object' par votre 'AbsenceResponse' lorsque le service sera prêt
        return ResponseEntity.ok(eleveService.obtenirAbsencesParEleve(id));
    }

    /**
     * Récupérer les notes d'un élève

    @GetMapping("/{id}/notes")
    public ResponseEntity<List<Object>> obtenirNotesParEleve(@PathVariable Long id) {
        // Remplacez 'Object' par votre 'NoteResponse' lorsque le service sera prêt
        return ResponseEntity.ok(eleveService.obtenirNotesParEleve(id));
    }*/
}