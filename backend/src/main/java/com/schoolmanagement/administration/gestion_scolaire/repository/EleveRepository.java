package com.schoolmanagement.administration.gestion_scolaire.repository;

import com.schoolmanagement.administration.gestion_scolaire.entity.Eleve;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EleveRepository extends JpaRepository<Eleve, Long> {
  List<Eleve> findByParentId(Long parentId);
}
