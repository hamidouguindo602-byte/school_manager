package com.schoolmanagement.administration.absences.service;

import com.schoolmanagement.administration.absences.dto.request.AbsenceRequest;
import com.schoolmanagement.administration.absences.dto.request.BatchJustificationRequest;
import com.schoolmanagement.administration.absences.dto.request.JustificationRequest;
import com.schoolmanagement.administration.absences.dto.response.AbsenceResponse;
import com.schoolmanagement.administration.absences.dto.response.AbsenceStatsResponse;
import com.schoolmanagement.administration.absences.entity.Absence;
import com.schoolmanagement.administration.absences.repository.AbsenceRepository;
import com.schoolmanagement.administration.emploi_temps.entity.EmploiDuTemps;
import com.schoolmanagement.administration.emploi_temps.repository.EmploiDuTempsRepository;
import com.schoolmanagement.authentication.entity.TypeRole;
import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.authentication.repository.UtilisateurRepository;
import com.schoolmanagement.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AbsenceService {
    private final AbsenceRepository absenceRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final EmploiDuTempsRepository edtRepository;

    @Transactional(readOnly = true)
    public List<AbsenceResponse> obtenirAbsencesEleve(Long eleveId) {
        return absenceRepository.findByEleveId(eleveId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AbsenceResponse> obtenirAbsencesParClasse(Long classeId) {
        return absenceRepository.findByClasseId(classeId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AbsenceResponse> obtenirAbsencesParClasseEtPeriode(Long classeId, LocalDate debut, LocalDate fin) {
        return absenceRepository.findByClasseIdAndDateBetween(classeId, debut, fin).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AbsenceResponse> obtenirAbsencesParEnseignant(Long enseignantId) {
        return absenceRepository.findByEnseignantId(enseignantId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AbsenceStatsResponse getStatsParClasse(Long classeId) {
        List<Absence> absences = absenceRepository.findByClasseId(classeId);
        long total = absences.size();
        long justifiees = absences.stream().filter(Absence::getJustifiee).count();
        long nonJustifiees = total - justifiees;
        double taux = total > 0 ? (double) nonJustifiees / total * 100 : 0.0;

        return AbsenceStatsResponse.builder()
                .total(total)
                .justifiees(justifiees)
                .nonJustifiees(nonJustifiees)
                .tauxAbsence(Math.round(taux * 100.0) / 100.0)
                .build();
    }

    @Transactional
    public AbsenceResponse declarerAbsence(AbsenceRequest req) {
        Utilisateur eleve = utilisateurRepository.findById(req.getEleveId())
                .orElseThrow(() -> new ResourceNotFoundException("Élève", req.getEleveId()));
        EmploiDuTemps edt = edtRepository.findById(req.getEmploiDuTempsId())
                .orElseThrow(() -> new ResourceNotFoundException("Créneau", req.getEmploiDuTempsId()));

        // Vérifier que l'élève a le rôle ELEVE
        if (eleve.getTypeRole() != TypeRole.ELEVE) {
            throw new IllegalArgumentException("Seuls les élèves peuvent avoir des absences");
        }

        Absence abs = Absence.builder()
                .eleve(eleve)
                .emploiDuTemps(edt)
                .dateAbsence(req.getDateAbsence())
                .justifiee(false)
                .build();
                
        return mapToDto(absenceRepository.save(abs));
    }

    @Transactional
    public AbsenceResponse justifierAbsence(Long id, JustificationRequest req) {
        Absence abs = absenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Absence", id));
                
        if (abs.getJustifiee()) {
            throw new IllegalArgumentException("Cette absence est déjà justifiée");
        }
                
        abs.setJustifiee(true);
        abs.setMotif(req.getMotif());
        abs.setDateJustification(LocalDate.now());
        
        return mapToDto(absenceRepository.save(abs));
    }

    @Transactional
    public List<AbsenceResponse> justifierEnLot(BatchJustificationRequest req) {
        List<Absence> absences = absenceRepository.findAllById(req.getAbsenceIds());
        if (absences.size() != req.getAbsenceIds().size()) {
            throw new ResourceNotFoundException("Absence", "Une ou plusieurs absences introuvables");
        }
        
        for (Absence abs : absences) {
            if (abs.getJustifiee()) {
                throw new IllegalArgumentException("Absence " + abs.getId() + " déjà justifiée");
            }
            abs.setJustifiee(true);
            abs.setMotif(req.getMotif());
            abs.setDateJustification(LocalDate.now());
        }
        
        return absenceRepository.saveAll(absences).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private AbsenceResponse mapToDto(Absence a) {
        var edt = a.getEmploiDuTemps();
        var assignation = edt.getAssignation();
        var classe = assignation.getClasse();
        var matiere = assignation.getMatiere();
        var enseignant = assignation.getEnseignant();
        
        return AbsenceResponse.builder()
                .id(a.getId())
                .eleveId(a.getEleve().getId())
                .nomEleve(a.getEleve().getNom())
                .prenomEleve(a.getEleve().getPrenom())
                .classeId(classe.getId())
                .nomClasse(classe.getNomClasse())
                .matiereId(matiere.getId())
                .nomMatiere(matiere.getNomMatiere())
                .enseignantId(enseignant.getId())
                .nomEnseignant(enseignant.getNom())
                .prenomEnseignant(enseignant.getPrenom())
                .jour(edt.getJour())
                .heureDebut(edt.getHeureDebut())
                .heureFin(edt.getHeureFin())
                .dateAbsence(a.getDateAbsence())
                .justifiee(a.getJustifiee())
                .motif(a.getMotif())
                .dateJustification(a.getDateJustification())
                .build();
    }
}