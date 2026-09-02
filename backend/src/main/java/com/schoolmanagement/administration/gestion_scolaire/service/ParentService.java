package com.schoolmanagement.administration.gestion_scolaire.service;

import com.schoolmanagement.administration.gestion_scolaire.dto.request.ParentCreationRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.ParentUpdateRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.ParentResponse;
import com.schoolmanagement.administration.gestion_scolaire.entity.Parent;
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
public class ParentService {

    private final ParentRepository parentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ParentResponse creerParent(ParentCreationRequest request) {
        Parent parent = Parent.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .numeroTelephone(request.getNumeroTelephone())
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .statut(StatutUtilisateur.ACTIF)
                .typeRole(TypeRole.PARENT)
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

        if (request.getNom() != null) parent.setNom(request.getNom());
        if (request.getPrenom() != null) parent.setPrenom(request.getPrenom());
        if (request.getNumeroTelephone() != null) parent.setNumeroTelephone(request.getNumeroTelephone());
        if (request.getEmail() != null) parent.setEmail(request.getEmail());

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
        return ParentResponse.builder()
                .id(parent.getId())
            .nom(parent.getNom())
            .prenom(parent.getPrenom())
            .numeroTelephone(parent.getNumeroTelephone())
            .email(parent.getEmail())
            .statut(parent.getStatut() != null ? parent.getStatut().name() : null)
                .build();
    }
}