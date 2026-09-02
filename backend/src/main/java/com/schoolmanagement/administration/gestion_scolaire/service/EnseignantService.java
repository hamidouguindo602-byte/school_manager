package com.schoolmanagement.administration.gestion_scolaire.service;

import com.schoolmanagement.administration.gestion_scolaire.dto.request.EnseignantCreationRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.EnseignantUpdateRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.EnseignantResponse;
import com.schoolmanagement.administration.gestion_scolaire.entity.Enseignant;
import com.schoolmanagement.administration.gestion_scolaire.repository.EnseignantRepository;
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
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public EnseignantResponse creerEnseignant(EnseignantCreationRequest request) {
        Enseignant enseignant = Enseignant.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .numeroTelephone(request.getNumeroTelephone())
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .statut(StatutUtilisateur.ACTIF)
                .typeRole(TypeRole.ENSEIGNANT)
                .specialite(request.getSpecialite())
                .build();

        Enseignant savedEnseignant = enseignantRepository.save(enseignant);
        return mapToResponse(savedEnseignant);
    }

    @Transactional(readOnly = true)
    public EnseignantResponse obtenirEnseignantParId(Long id) {
        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enseignant introuvable avec l'ID : " + id));
        return mapToResponse(enseignant);
    }

    @Transactional(readOnly = true)
    public List<EnseignantResponse> listerTousLesEnseignants() {
        return enseignantRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public EnseignantResponse mettreAJourEnseignant(Long id, EnseignantUpdateRequest request) {
        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enseignant introuvable avec l'ID : " + id));

        if (request.getNom() != null) enseignant.setNom(request.getNom());
        if (request.getPrenom() != null) enseignant.setPrenom(request.getPrenom());
        if (request.getNumeroTelephone() != null) enseignant.setNumeroTelephone(request.getNumeroTelephone());
        if (request.getEmail() != null) enseignant.setEmail(request.getEmail());

        if (request.getSpecialite() != null) {
            enseignant.setSpecialite(request.getSpecialite());
        }

        Enseignant updatedEnseignant = enseignantRepository.save(enseignant);
        return mapToResponse(updatedEnseignant);
    }

    @Transactional
    public void supprimerEnseignant(Long id) {
        if (!enseignantRepository.existsById(id)) {
            throw new EntityNotFoundException("Enseignant introuvable avec l'ID : " + id);
        }
        enseignantRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Object> obtenirEmploisDuTempsParEnseignant(Long id) {
        if (!enseignantRepository.existsById(id)) {
            throw new EntityNotFoundException("Enseignant introuvable avec l'ID : " + id);
        }
        // TODO: Remplacer par la logique réelle du repository d'emplois du temps
        return Collections.emptyList();
    }

    @Transactional(readOnly = true)
    public List<Object> obtenirEvaluationsParEnseignant(Long id) {
        if (!enseignantRepository.existsById(id)) {
            throw new EntityNotFoundException("Enseignant introuvable avec l'ID : " + id);
        }
        // TODO: Remplacer par la logique réelle du repository d'évaluations
        return Collections.emptyList();
    }

    private EnseignantResponse mapToResponse(Enseignant enseignant) {
        return EnseignantResponse.builder()
                .id(enseignant.getId())
            .nom(enseignant.getNom())
            .prenom(enseignant.getPrenom())
            .numeroTelephone(enseignant.getNumeroTelephone())
            .email(enseignant.getEmail())
            .statut(enseignant.getStatut() != null ? enseignant.getStatut().name() : null)
                .specialite(enseignant.getSpecialite())
                .build();
    }
}