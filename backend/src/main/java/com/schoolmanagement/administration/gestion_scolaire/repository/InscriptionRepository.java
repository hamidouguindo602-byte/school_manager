package com.schoolmanagement.administration.gestion_scolaire.repository;

import com.schoolmanagement.administration.gestion_scolaire.entity.Inscription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
  List<Inscription> findByEleveId(Long eleveId);

  List<Inscription> findByFormationId(Long formationId);
}
