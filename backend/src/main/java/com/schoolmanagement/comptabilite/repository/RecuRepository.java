package com.schoolmanagement.comptabilite.repository;

import com.schoolmanagement.comptabilite.entity.Recu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecuRepository extends JpaRepository<Recu, Long> {

    Optional<Recu> findByPaiementIdPaiement(Long idPaiement);

    boolean existsByPaiementIdPaiement(Long idPaiement);
}