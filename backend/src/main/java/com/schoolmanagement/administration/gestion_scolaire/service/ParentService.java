package com.schoolmanagement.administration.gestion_scolaire.service;

import com.schoolmanagement.common.audit.JournaliserAutomatiquement;

import com.schoolmanagement.administration.gestion_scolaire.dto.request.ParentCreationRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.ParentUpdateRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.EleveResponse;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.ParentResponse;
import com.schoolmanagement.administration.gestion_scolaire.entity.Eleve;
import com.schoolmanagement.administration.gestion_scolaire.entity.Parent;
import com.schoolmanagement.administration.gestion_scolaire.repository.EleveRepository;
import com.schoolmanagement.administration.gestion_scolaire.repository.ParentRepository;
import com.schoolmanagement.authentication.entity.StatutUtilisateur;
import com.schoolmanagement.authentication.entity.TypeRole;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@JournaliserAutomatiquement
public class ParentService {

  private final ParentRepository parentRepository;
  private final EleveRepository eleveRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public ParentResponse creerParent(ParentCreationRequest request) {
    Parent parent =
        Parent.builder()
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
    Parent parent =
        parentRepository
            .findById(id)
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
    Parent parent =
        parentRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Parent introuvable avec l'ID : " + id));

    if (request.getNom() != null) parent.setNom(request.getNom());
    if (request.getPrenom() != null) parent.setPrenom(request.getPrenom());
    if (request.getNumeroTelephone() != null)
      parent.setNumeroTelephone(request.getNumeroTelephone());
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
  public List<EleveResponse> obtenirElevesParParent(Long id) {
    if (!parentRepository.existsById(id)) {
      throw new EntityNotFoundException("Parent introuvable avec l'ID : " + id);
    }
    return eleveRepository.findByParentId(id).stream()
        .map(eleve -> mapEleveToResponse(eleve, id))
        .toList();
  }

  private EleveResponse mapEleveToResponse(Eleve eleve, Long parentId) {
    return EleveResponse.builder()
        .id(eleve.getId())
        .nom(eleve.getNom())
        .prenom(eleve.getPrenom())
        .numeroTelephone(eleve.getNumeroTelephone())
        .email(eleve.getEmail())
        .statut(eleve.getStatut() != null ? eleve.getStatut().name() : null)
        .dateNaissance(eleve.getDateNaissance())
        .adresse(eleve.getAdresse())
        .idParent(parentId)
        .build();
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
