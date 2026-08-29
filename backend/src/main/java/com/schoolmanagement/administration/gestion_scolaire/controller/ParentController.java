package com.schoolmanagement.administration.gestion_scolaire.controller;

import com.schoolmanagement.administration.gestion_scolaire.dto.request.ParentCreationRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.ParentUpdateRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.ParentResponse;
import com.schoolmanagement.administration.gestion_scolaire.service.ParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;

    @PostMapping
    public ResponseEntity<ParentResponse> creerParent(@Valid @RequestBody ParentCreationRequest request) {
        return new ResponseEntity<>(parentService.creerParent(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentResponse> obtenirParentParId(@PathVariable Long id) {
        return ResponseEntity.ok(parentService.obtenirParentParId(id));
    }

    @GetMapping
    public ResponseEntity<List<ParentResponse>> listerTousLesParents() {
        return ResponseEntity.ok(parentService.listerTousLesParents());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentResponse> mettreAJourParent(@PathVariable Long id, @Valid @RequestBody ParentUpdateRequest request) {
        return ResponseEntity.ok(parentService.mettreAJourParent(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerParent(@PathVariable Long id) {
        parentService.supprimerParent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/eleves")
    public ResponseEntity<List<Object>> obtenirElevesParParent(@PathVariable Long id) {
        return ResponseEntity.ok(parentService.obtenirElevesParParent(id));
    }
}