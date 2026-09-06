package com.schoolmanagement.comptabilite.repository;

import com.schoolmanagement.comptabilite.entity.Echeance;
import com.schoolmanagement.comptabilite.entity.StatutEcheance;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcheanceRepository extends JpaRepository<Echeance, Long> {

  List<Echeance> findByInscriptionId(Long idInscription);

  List<Echeance> findByStatut(StatutEcheance statut);

  List<Echeance> findByDateLimiteBeforeAndStatutNot(LocalDate date, StatutEcheance statut);
}
