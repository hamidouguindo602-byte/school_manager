package com.schoolmanagement.administration.gestion_scolaire.service;

import com.schoolmanagement.administration.gestion_scolaire.dto.request.EnseignantCreationRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.EnseignantUpdateRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.EnseignantResponse;
import com.schoolmanagement.administration.gestion_scolaire.entity.Enseignant;
import com.schoolmanagement.administration.gestion_scolaire.repository.EnseignantRepository;
import com.schoolmanagement.authentication.entity.StatutUtilisateur;
import com.schoolmanagement.authentication.entity.TypeRole;
import com.schoolmanagement.authentication.entity.Utilisateur;
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
        Utilisateur utilisateur = Utilisateur.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .numeroTelephone(request.getNumeroTelephone())
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .statut(StatutUtilisateur.ACTIF)
                .typeRole(TypeRole.ENSEIGNANT)
                .build();

        Enseignant enseignant = Enseignant.builder()
                .specialite(request.getSpecialite())
                .utilisateur(utilisateur)
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

        Utilisateur utilisateur = enseignant.getUtilisateur();
        if (request.getNom() != null) utilisateur.setNom(request.getNom());
        if (request.getPrenom() != null) utilisateur.setPrenom(request.getPrenom());
        if (request.getNumeroTelephone() != null) utilisateur.setNumeroTelephone(request.getNumeroTelephone());
        if (request.getEmail() != null) utilisateur.setEmail(request.getEmail());

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
        Utilisateur u = enseignant.getUtilisateur();
        return EnseignantResponse.builder()
                .id(enseignant.getId())
                .nom(u != null ? u.getNom() : null)
                .prenom(u != null ? u.getPrenom() : null)
                .numeroTelephone(u != null ? u.getNumeroTelephone() : null)
                .email(u != null ? u.getEmail() : null)
                .statut(u != null && u.getStatut() != null ? u.getStatut().name() : null)
                .specialite(enseignant.getSpecialite())
                .build();
    }
}