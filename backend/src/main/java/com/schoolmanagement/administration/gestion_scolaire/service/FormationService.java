package com.schoolmanagement.administration.gestion_scolaire.service;

import com.schoolmanagement.administration.gestion_scolaire.dto.request.FormationCreationRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.FormationUpdateRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.FormationResponse;
import com.schoolmanagement.administration.gestion_scolaire.entity.Formation;
import com.schoolmanagement.administration.gestion_scolaire.repository.FormationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormationService {

    private final FormationRepository formationRepository;

    @Transactional
    public FormationResponse creerFormation(FormationCreationRequest request) {
        Formation formation = Formation.builder()
                .nomFormation(request.getNomFormation())
                .description(request.getDescription())
                .duree(request.getDuree())
                .build();

        Formation savedFormation = formationRepository.save(formation);
        return mapToResponse(savedFormation);
    }

    @Transactional(readOnly = true)
    public FormationResponse obtenirFormationParId(Long id) {
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Formation introuvable avec l'ID : " + id));
        return mapToResponse(formation);
    }

    @Transactional(readOnly = true)
    public List<FormationResponse> listerToutesLesFormations() {
        return formationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public FormationResponse mettreAJourFormation(Long id, FormationUpdateRequest request) {
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Formation introuvable avec l'ID : " + id));

        if (request.getNomFormation() != null) formation.setNomFormation(request.getNomFormation());
        if (request.getDescription() != null) formation.setDescription(request.getDescription());
        if (request.getDuree() != null) formation.setDuree(request.getDuree());

        Formation updatedFormation = formationRepository.save(formation);
        return mapToResponse(updatedFormation);
    }

    @Transactional
    public void supprimerFormation(Long id) {
        if (!formationRepository.existsById(id)) {
            throw new EntityNotFoundException("Formation introuvable avec l'ID : " + id);
        }
        formationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Object> obtenirClassesParFormation(Long id) {
        if (!formationRepository.existsById(id)) {
            throw new EntityNotFoundException("Formation introuvable avec l'ID : " + id);
        }
        // TODO: Remplacer par la logique réelle du repository de classes
        return Collections.emptyList();
    }

    private FormationResponse mapToResponse(Formation formation) {
        return FormationResponse.builder()
                .id(formation.getId())
                .nomFormation(formation.getNomFormation())
                .description(formation.getDescription())
                .duree(formation.getDuree())
                .build();
    }
}