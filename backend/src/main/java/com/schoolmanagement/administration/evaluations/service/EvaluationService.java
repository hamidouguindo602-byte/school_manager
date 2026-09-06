package com.schoolmanagement.administration.evaluations.service;

import com.schoolmanagement.common.audit.JournaliserAutomatiquement;

import com.schoolmanagement.administration.emploi_temps.repository.ClasseRepository;
import com.schoolmanagement.administration.emploi_temps.repository.MatiereRepository;
import com.schoolmanagement.administration.evaluations.dto.request.EvaluationRequest;
import com.schoolmanagement.administration.evaluations.dto.response.EvaluationResponse;
import com.schoolmanagement.administration.evaluations.entity.Evaluation;
import com.schoolmanagement.administration.evaluations.repository.EvaluationRepository;
import com.schoolmanagement.administration.gestion_scolaire.repository.EnseignantRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@JournaliserAutomatiquement
public class EvaluationService {

  private final EvaluationRepository evaluationRepository;
  private final MatiereRepository matiereRepository;
  private final EnseignantRepository enseignantRepository;
  private final ClasseRepository classeRepository;

  @Transactional(readOnly = true)
  public List<EvaluationResponse> findAll() {
    return evaluationRepository.findAll().stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public EvaluationResponse findById(Long id) {
    Evaluation evaluation =
        evaluationRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Évaluation introuvable avec l'ID : " + id));
    return mapToResponse(evaluation);
  }

  @Transactional(readOnly = true)
  public List<EvaluationResponse> findByClasse(Long idClasse) {
    return evaluationRepository.findByClasseId(idClasse).stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<EvaluationResponse> findByEnseignant(Long idEnseignant) {
    return evaluationRepository.findByEnseignantId(idEnseignant).stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Transactional
  public EvaluationResponse create(EvaluationRequest request) {
    Evaluation evaluation =
        Evaluation.builder()
            .dateEvaluation(request.getDateEvaluation())
            .type(request.getType())
            .matiere(
                matiereRepository
                    .findById(request.getIdMatiere())
                    .orElseThrow(() -> new EntityNotFoundException("Matière introuvable")))
            .enseignant(
                enseignantRepository
                    .findById(request.getIdEnseignant())
                    .orElseThrow(() -> new EntityNotFoundException("Enseignant introuvable")))
            .classe(
                classeRepository
                    .findById(request.getIdClasse())
                    .orElseThrow(() -> new EntityNotFoundException("Classe introuvable")))
            .build();

    return mapToResponse(evaluationRepository.save(evaluation));
  }

  @Transactional
  public EvaluationResponse update(Long id, EvaluationRequest request) {
    Evaluation evaluation =
        evaluationRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Évaluation introuvable avec l'ID : " + id));

    evaluation.setDateEvaluation(request.getDateEvaluation());
    evaluation.setType(request.getType());
    evaluation.setMatiere(
        matiereRepository
            .findById(request.getIdMatiere())
            .orElseThrow(() -> new EntityNotFoundException("Matière introuvable")));
    evaluation.setEnseignant(
        enseignantRepository
            .findById(request.getIdEnseignant())
            .orElseThrow(() -> new EntityNotFoundException("Enseignant introuvable")));
    evaluation.setClasse(
        classeRepository
            .findById(request.getIdClasse())
            .orElseThrow(() -> new EntityNotFoundException("Classe introuvable")));

    return mapToResponse(evaluationRepository.save(evaluation));
  }

  @Transactional
  public void delete(Long id) {
    if (!evaluationRepository.existsById(id)) {
      throw new EntityNotFoundException("Évaluation introuvable avec l'ID : " + id);
    }
    evaluationRepository.deleteById(id);
  }

  private EvaluationResponse mapToResponse(Evaluation evaluation) {
    return EvaluationResponse.builder()
        .id(evaluation.getId())
        .dateEvaluation(evaluation.getDateEvaluation())
        .type(evaluation.getType())
        .idMatiere(evaluation.getMatiere().getId())
        .nomMatiere(evaluation.getMatiere().getNomMatiere())
        .idEnseignant(evaluation.getEnseignant().getId())
        .idClasse(evaluation.getClasse().getId())
        .nomClasse(evaluation.getClasse().getNomClasse())
        .build();
  }
}
