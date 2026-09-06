package com.schoolmanagement.administration.evaluations.repository;

import com.schoolmanagement.administration.evaluations.entity.Evaluation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
  List<Evaluation> findByClasseId(Long idClasse);

  List<Evaluation> findByEnseignantId(Long idEnseignant);
}
