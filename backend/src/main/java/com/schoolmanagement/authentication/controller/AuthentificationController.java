package com.schoolmanagement.authentication.controller;

import com.schoolmanagement.authentication.dto.request.ConnexionRequest;
import com.schoolmanagement.authentication.dto.request.DemandeReinitialisationMotDePasseRequest;
import com.schoolmanagement.authentication.dto.request.ModificationMotDePasseRequest;
import com.schoolmanagement.authentication.dto.request.ReinitialisationMotDePasseRequest;
import com.schoolmanagement.authentication.dto.response.ConnexionResponse;
import com.schoolmanagement.authentication.service.AuthentificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/authentification", "/api/authentifications"})
@RequiredArgsConstructor
public class AuthentificationController {

    private final AuthentificationService authentificationService;

    @PostMapping("/connexion")
    public ConnexionResponse connexion(
            @Valid @RequestBody ConnexionRequest request) {

        return authentificationService.connexion(request);
    }

    @PutMapping("/modifier-mot-de-passe")
    public String modifierMotDePasse(
            @Valid @RequestBody ModificationMotDePasseRequest request) {

        return authentificationService.modifierMotDePasse(request);
    }

    @PostMapping("/mot-de-passe-oublie")
    public String demanderReinitialisation(
            @Valid @RequestBody DemandeReinitialisationMotDePasseRequest request) {

        return authentificationService.demanderReinitialisation(request);
    }

    @PostMapping("/reinitialiser-mot-de-passe")
    public String reinitialiserMotDePasse(
            @Valid @RequestBody ReinitialisationMotDePasseRequest request) {

        return authentificationService.reinitialiserMotDePasse(request);
    }

}