package com.schoolmanagement.administration.evaluations.service;

import com.schoolmanagement.common.audit.JournaliserAutomatiquement;

import com.schoolmanagement.administration.evaluations.dto.request.NoteRequest;
import com.schoolmanagement.administration.evaluations.dto.response.NoteResponse;
import com.schoolmanagement.administration.evaluations.entity.Evaluation;
import com.schoolmanagement.administration.evaluations.entity.Note;
import com.schoolmanagement.administration.evaluations.repository.EvaluationRepository;
import com.schoolmanagement.administration.evaluations.repository.NoteRepository;
import com.schoolmanagement.administration.gestion_scolaire.entity.Eleve;
import com.schoolmanagement.administration.gestion_scolaire.repository.EleveRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@JournaliserAutomatiquement
public class NoteService {

  private final NoteRepository noteRepository;
  private final EvaluationRepository evaluationRepository;
  private final EleveRepository eleveRepository;

  @Transactional(readOnly = true)
  public List<NoteResponse> findAll() {
    return noteRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public NoteResponse findById(Long id) {
    Note note =
        noteRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Note introuvable avec l'ID : " + id));
    return mapToResponse(note);
  }

  @Transactional(readOnly = true)
  public List<NoteResponse> findByEleve(Long idEleve) {
    return noteRepository.findByEleveId(idEleve).stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<NoteResponse> findByEvaluation(Long idEvaluation) {
    return noteRepository.findByEvaluationId(idEvaluation).stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Transactional
  public NoteResponse create(NoteRequest request) {
    Evaluation evaluation =
        evaluationRepository
            .findById(request.getIdEvaluation())
            .orElseThrow(() -> new EntityNotFoundException("Évaluation introuvable"));
    Eleve eleve =
        eleveRepository
            .findById(request.getIdEleve())
            .orElseThrow(() -> new EntityNotFoundException("Élève introuvable"));

    noteRepository
        .findByEvaluationIdAndEleveId(request.getIdEvaluation(), request.getIdEleve())
        .ifPresent(
            n -> {
              throw new IllegalArgumentException(
                  "Une note existe déjà pour cet élève dans cette évaluation.");
            });

    Note note =
        Note.builder()
            .valeur(request.getValeur())
            .dateSaisie(LocalDate.now())
            .evaluation(evaluation)
            .eleve(eleve)
            .build();

    return mapToResponse(noteRepository.save(note));
  }

  @Transactional
  public NoteResponse update(Long id, NoteRequest request) {
    Note note =
        noteRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Note introuvable avec l'ID : " + id));

    note.setValeur(request.getValeur());
    return mapToResponse(noteRepository.save(note));
  }

  @Transactional
  public void delete(Long id) {
    if (!noteRepository.existsById(id)) {
      throw new EntityNotFoundException("Note introuvable avec l'ID : " + id);
    }
    noteRepository.deleteById(id);
  }

  private NoteResponse mapToResponse(Note note) {
    return NoteResponse.builder()
        .id(note.getId())
        .valeur(note.getValeur())
        .dateSaisie(note.getDateSaisie())
        .idEvaluation(note.getEvaluation().getId())
        .idEleve(note.getEleve().getId())
        .build();
  }
}
