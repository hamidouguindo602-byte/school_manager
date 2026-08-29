package com.schoolmanagement.administration.evaluations.repository;

import com.schoolmanagement.administration.evaluations.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByEleveId(Long idEleve);
    List<Note> findByEvaluationId(Long idEvaluation);
    Optional<Note> findByEvaluationIdAndEleveId(Long idEvaluation, Long idEleve);
}