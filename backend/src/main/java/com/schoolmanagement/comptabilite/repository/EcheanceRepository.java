package com.schoolmanagement.comptabilite.repository;

import com.schoolmanagement.comptabilite.entity.Echeance;
import com.schoolmanagement.comptabilite.entity.StatutEcheance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EcheanceRepository extends JpaRepository<Echeance, Long> {

    List<Echeance> findByInscriptionId(Long idInscription);

    List<Echeance> findByStatut(StatutEcheance statut);

    List<Echeance> findByDateLimiteBeforeAndStatutNot(
            LocalDate date,
            StatutEcheance statut
    );
}