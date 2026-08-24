package com.schoolmanagement.administration.emploi_temps.service;

import com.schoolmanagement.administration.emploi_temps.dto.request.EmploiDuTempsRequest;
import com.schoolmanagement.administration.emploi_temps.dto.response.EmploiDuTempsResponse;
import com.schoolmanagement.administration.emploi_temps.entity.Assignation;
import com.schoolmanagement.administration.emploi_temps.entity.EmploiDuTemps;
import com.schoolmanagement.administration.emploi_temps.exception.ConflitHoraireException;
import com.schoolmanagement.administration.emploi_temps.repository.AssignationRepository;
import com.schoolmanagement.administration.emploi_temps.repository.EmploiDuTempsRepository;
import com.schoolmanagement.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmploiDuTempsService {

    private final EmploiDuTempsRepository edtRepository;
    private final AssignationRepository assignationRepository;

    @Transactional(readOnly = true)
    public List<EmploiDuTempsResponse> obtenirParClasse(Long classeId) {
        return edtRepository.findAll().stream()
                .filter(e -> e.getAssignation().getClasse().getId().equals(classeId))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<String, List<EmploiDuTempsResponse>> obtenirGrilleParClasse(Long classeId) {
        return obtenirParClasse(classeId).stream()
                .collect(Collectors.groupingBy(
                        EmploiDuTempsResponse::getJour,
                        () -> new LinkedHashMap<>(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(EmploiDuTempsResponse::getHeureDebut))
                                        .toList()
                        )
                ));
    }

    @Transactional
    public EmploiDuTempsResponse creerCreneau(EmploiDuTempsRequest request) {
        validerCreneau(request, null);
        EmploiDuTemps edt = construireEdtDepuisRequest(new EmploiDuTemps(), request);
        return mapToDto(edtRepository.save(edt));
    }

    @Transactional
    public EmploiDuTempsResponse modifierCreneau(Long id, EmploiDuTempsRequest request) {
        validerCreneau(request, id);
        EmploiDuTemps edtExistant = edtRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Créneau d'emploi du temps", id));
        
        EmploiDuTemps edtMisAJour = construireEdtDepuisRequest(edtExistant, request);
        return mapToDto(edtRepository.save(edtMisAJour));
    }

    @Transactional
    public void supprimerCreneau(Long id) {
        if (!edtRepository.existsById(id)) {
            throw new ResourceNotFoundException("Créneau d'emploi du temps", id);
        }
        edtRepository.deleteById(id);
    }

    private void validerCreneau(EmploiDuTempsRequest request, Long excludeId) {
        if (edtRepository.existsConflitHoraire(
                request.getAssignationId(),
                request.getJour(),
                request.getHeureDebut(),
                request.getHeureFin(),
                excludeId
        )) {
            throw new ConflitHoraireException("Conflit horaire : un créneau existe déjà sur cet intervalle pour cette assignation");
        }
    }

    private EmploiDuTemps construireEdtDepuisRequest(EmploiDuTemps edt, EmploiDuTempsRequest request) {
        Assignation assignation = assignationRepository.findById(request.getAssignationId())
                .orElseThrow(() -> new ResourceNotFoundException("Assignation", request.getAssignationId()));

        edt.setAssignation(assignation);
        edt.setJour(request.getJour());
        edt.setHeureDebut(request.getHeureDebut());
        edt.setHeureFin(request.getHeureFin());

        return edt;
    }

    private EmploiDuTempsResponse mapToDto(EmploiDuTemps e) {
        EmploiDuTempsResponse dto = new EmploiDuTempsResponse();
        dto.setId(e.getId());
        
        dto.setNomClasse(e.getAssignation().getClasse().getNomClasse());
        dto.setNomMatiere(e.getAssignation().getMatiere().getNomMatiere());
        dto.setNomEnseignant(e.getAssignation().getEnseignant().getNom());
        dto.setPrenomEnseignant(e.getAssignation().getEnseignant().getPrenom());
        
        dto.setJour(e.getJour());
        dto.setHeureDebut(e.getHeureDebut());
        dto.setHeureFin(e.getHeureFin());
        return dto;
    }
}