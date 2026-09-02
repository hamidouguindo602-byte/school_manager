package com.schoolmanagement.administration.gestion_scolaire.service;

import com.schoolmanagement.administration.gestion_scolaire.dto.request.EleveCreationRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.EleveUpdateRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.EleveResponse;
import com.schoolmanagement.administration.gestion_scolaire.entity.Eleve;
import com.schoolmanagement.administration.gestion_scolaire.entity.Parent;
import com.schoolmanagement.administration.gestion_scolaire.repository.EleveRepository;
import com.schoolmanagement.administration.gestion_scolaire.repository.ParentRepository;
import com.schoolmanagement.authentication.entity.StatutUtilisateur;
import com.schoolmanagement.authentication.entity.TypeRole;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EleveService {

    private final EleveRepository eleveRepository;
    private final ParentRepository parentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public EleveResponse creerEleve(EleveCreationRequest request) {
        Parent parent = null;
        if (request.getIdParent() != null) {
            parent = parentRepository.findById(request.getIdParent())
                    .orElseThrow(() -> new EntityNotFoundException("Parent introuvable avec l'ID : " + request.getIdParent()));
        }

        Eleve eleve = Eleve.builder()
            .nom(request.getNom())
            .prenom(request.getPrenom())
            .numeroTelephone(request.getNumeroTelephone())
            .email(request.getEmail())
            .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
            .statut(StatutUtilisateur.ACTIF)
            .typeRole(TypeRole.ELEVE)
            .dateNaissance(request.getDateNaissance())
            .adresse(request.getAdresse())
            .parent(parent)
            .build();

        Eleve savedEleve = eleveRepository.save(eleve);
        return mapToResponse(savedEleve);
    }

    @Transactional(readOnly = true)
    public EleveResponse obtenirEleveParId(Long id) {
        Eleve eleve = eleveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Élève introuvable avec l'ID : " + id));
        return mapToResponse(eleve);
    }

    @Transactional(readOnly = true)
    public List<EleveResponse> listerTousLesEleves() {
        return eleveRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public EleveResponse mettreAJourEleve(Long id, EleveUpdateRequest request) {
        Eleve eleve = eleveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Élève introuvable avec l'ID : " + id));

        if (request.getNom() != null) {
            eleve.setNom(request.getNom());
        }
        if (request.getPrenom() != null) {
            eleve.setPrenom(request.getPrenom());
        }
        if (request.getNumeroTelephone() != null) {
            eleve.setNumeroTelephone(request.getNumeroTelephone());
        }
        if (request.getEmail() != null) {
            eleve.setEmail(request.getEmail());
        }

        if (request.getDateNaissance() != null) {
            eleve.setDateNaissance(request.getDateNaissance());
        }
        if (request.getAdresse() != null) {
            eleve.setAdresse(request.getAdresse());
        }

        if (request.getIdParent() != null) {
            Parent parent = parentRepository.findById(request.getIdParent())
                    .orElseThrow(() -> new EntityNotFoundException("Parent introuvable avec l'ID : " + request.getIdParent()));
            eleve.setParent(parent);
        }

        Eleve updatedEleve = eleveRepository.save(eleve);
        return mapToResponse(updatedEleve);
    }

    @Transactional
    public void supprimerEleve(Long id) {
        if (!eleveRepository.existsById(id)) {
            throw new EntityNotFoundException("Élève introuvable avec l'ID : " + id);
        }
        eleveRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Object> obtenirInscriptionsParEleve(Long id) {
        if (!eleveRepository.existsById(id)) {
            throw new EntityNotFoundException("Élève introuvable avec l'ID : " + id);
        }
        // TODO: Remplacer par la logique réelle du repository d'inscriptions
        return Collections.emptyList();
    }

    @Transactional(readOnly = true)
    public List<Object> obtenirAbsencesParEleve(Long id) {
        if (!eleveRepository.existsById(id)) {
            throw new EntityNotFoundException("Élève introuvable avec l'ID : " + id);
        }
        // TODO: Remplacer par la logique réelle du repository d'absences
        return Collections.emptyList();
    }

    @Transactional(readOnly = true)
    public List<Object> obtenirNotesParEleve(Long id) {
        if (!eleveRepository.existsById(id)) {
            throw new EntityNotFoundException("Élève introuvable avec l'ID : " + id);
        }
        // TODO: Remplacer par la logique réelle du repository de notes
        return Collections.emptyList();
    }

    private EleveResponse mapToResponse(Eleve eleve) {
        return EleveResponse.builder()
                .id(eleve.getId())
            .nom(eleve.getNom())
            .prenom(eleve.getPrenom())
            .numeroTelephone(eleve.getNumeroTelephone())
            .email(eleve.getEmail())
            .statut(eleve.getStatut() != null ? eleve.getStatut().name() : null)
                .dateNaissance(eleve.getDateNaissance())
                .adresse(eleve.getAdresse())
                .idParent(eleve.getParent() != null ? eleve.getParent().getId() : null)
                .build();
    }
}