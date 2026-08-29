package com.schoolmanagement.administration.gestion_scolaire.service;

import com.schoolmanagement.administration.gestion_scolaire.dto.request.ParentCreationRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.ParentUpdateRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.ParentResponse;
import com.schoolmanagement.administration.gestion_scolaire.entity.Parent;
import com.schoolmanagement.administration.gestion_scolaire.repository.ParentRepository;
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
public class ParentService {

    private final ParentRepository parentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ParentResponse creerParent(ParentCreationRequest request) {
        Utilisateur utilisateur = Utilisateur.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .numeroTelephone(request.getNumeroTelephone())
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .statut(StatutUtilisateur.ACTIF)
                .typeRole(TypeRole.PARENT)
                .build();

        Parent parent = Parent.builder()
                .utilisateur(utilisateur)
                .build();

        Parent savedParent = parentRepository.save(parent);
        return mapToResponse(savedParent);
    }

    @Transactional(readOnly = true)
    public ParentResponse obtenirParentParId(Long id) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parent introuvable avec l'ID : " + id));
        return mapToResponse(parent);
    }

    @Transactional(readOnly = true)
    public List<ParentResponse> listerTousLesParents() {
        return parentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ParentResponse mettreAJourParent(Long id, ParentUpdateRequest request) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parent introuvable avec l'ID : " + id));

        Utilisateur utilisateur = parent.getUtilisateur();
        if (request.getNom() != null) utilisateur.setNom(request.getNom());
        if (request.getPrenom() != null) utilisateur.setPrenom(request.getPrenom());
        if (request.getNumeroTelephone() != null) utilisateur.setNumeroTelephone(request.getNumeroTelephone());
        if (request.getEmail() != null) utilisateur.setEmail(request.getEmail());

        Parent updatedParent = parentRepository.save(parent);
        return mapToResponse(updatedParent);
    }

    @Transactional
    public void supprimerParent(Long id) {
        if (!parentRepository.existsById(id)) {
            throw new EntityNotFoundException("Parent introuvable avec l'ID : " + id);
        }
        parentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Object> obtenirElevesParParent(Long id) {
        if (!parentRepository.existsById(id)) {
            throw new EntityNotFoundException("Parent introuvable avec l'ID : " + id);
        }
        // TODO: Remplacer par la logique réelle du repository d'élèves rattachés au parent
        return Collections.emptyList();
    }

    private ParentResponse mapToResponse(Parent parent) {
        Utilisateur u = parent.getUtilisateur();
        return ParentResponse.builder()
                .id(parent.getId())
                .nom(u != null ? u.getNom() : null)
                .prenom(u != null ? u.getPrenom() : null)
                .numeroTelephone(u != null ? u.getNumeroTelephone() : null)
                .email(u != null ? u.getEmail() : null)
                .statut(u != null && u.getStatut() != null ? u.getStatut().name() : null)
                .build();
    }
}