package com.schoolmanagement.administration.emploi_temps.service;

import com.schoolmanagement.common.audit.JournaliserAutomatiquement;

import com.schoolmanagement.administration.emploi_temps.dto.request.AssignationRequest;
import com.schoolmanagement.administration.emploi_temps.dto.response.AssignationResponse;
import com.schoolmanagement.administration.emploi_temps.entity.Assignation;
import com.schoolmanagement.administration.emploi_temps.repository.AssignationRepository;

// Importez vos entités et repositories existants (ajustez les packages si nécessaire)
import com.schoolmanagement.administration.emploi_temps.entity.Classe;
import com.schoolmanagement.administration.emploi_temps.repository.ClasseRepository;
import com.schoolmanagement.administration.emploi_temps.entity.Matiere;
import com.schoolmanagement.administration.emploi_temps.repository.MatiereRepository;
import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.authentication.repository.UtilisateurRepository;
import com.schoolmanagement.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@JournaliserAutomatiquement
public class AssignationService {

    private final AssignationRepository assignationRepository;
    private final ClasseRepository classeRepository;
    private final MatiereRepository matiereRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Transactional
    public AssignationResponse creerAssignation(AssignationRequest request) {
        Classe classe = classeRepository.findById(request.getClasseId())
                .orElseThrow(() -> new ResourceNotFoundException("Classe", request.getClasseId()));
        
        Matiere matiere = matiereRepository.findById(request.getMatiereId())
                .orElseThrow(() -> new ResourceNotFoundException("Matière", request.getMatiereId()));
        
        Utilisateur enseignant = utilisateurRepository.findById(request.getEnseignantId())
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant", request.getEnseignantId()));

        Assignation assignation = Assignation.builder()
                .classe(classe)
                .matiere(matiere)
                .enseignant(enseignant)
                .heuresHebdomadaires(request.getHeuresHebdomadaires())
                .build();

        Assignation saved = assignationRepository.save(assignation);
        return mapToResponse(saved);
    }

    @Transactional
    public AssignationResponse modifierAssignation(Long id, AssignationRequest request) {
        Assignation assignation = assignationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignation", id));

        Classe classe = classeRepository.findById(request.getClasseId())
                .orElseThrow(() -> new ResourceNotFoundException("Classe", request.getClasseId()));
        
        Matiere matiere = matiereRepository.findById(request.getMatiereId())
                .orElseThrow(() -> new ResourceNotFoundException("Matière", request.getMatiereId()));
        
        Utilisateur enseignant = utilisateurRepository.findById(request.getEnseignantId())
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant", request.getEnseignantId()));

        assignation.setClasse(classe);
        assignation.setMatiere(matiere);
        assignation.setEnseignant(enseignant);
        assignation.setHeuresHebdomadaires(request.getHeuresHebdomadaires());

        Assignation updated = assignationRepository.save(assignation);
        return mapToResponse(updated);
    }

        @Transactional
        public List<AssignationResponse> listerToutes() {
        return assignationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

        @Transactional
        public List<AssignationResponse> listerParClasse(Long classeId) {
        return assignationRepository.findByClasseId(classeId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void supprimerAssignation(Long id) {
        if (!assignationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Assignation", id);
        }
        assignationRepository.deleteById(id);
    }

    private AssignationResponse mapToResponse(Assignation assignation) {
        return AssignationResponse.builder()
                .id(assignation.getId())
                .classeId(assignation.getClasse().getId())
                .nomClasse(assignation.getClasse().getNomClasse()) // Corrigé ici
                .matiereId(assignation.getMatiere().getId())
                .nomMatiere(assignation.getMatiere().getNomMatiere()) // Corrigé ici
                .enseignantId(assignation.getEnseignant().getId())
                .nomEnseignant(assignation.getEnseignant().getNom() + " " + assignation.getEnseignant().getPrenom())
                .heuresHebdomadaires(assignation.getHeuresHebdomadaires())
                .build();
    }
}