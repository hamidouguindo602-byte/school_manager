package com.schoolmanagement.authentication.repository;

import com.schoolmanagement.authentication.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

   // boolean existsByEmail(String email);
}