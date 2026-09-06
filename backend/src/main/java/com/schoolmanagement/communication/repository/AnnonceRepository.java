package com.schoolmanagement.communication.repository;

import com.schoolmanagement.communication.entity.Annonce;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {

  @Override
  @EntityGraph(attributePaths = {"auteur"})
  List<Annonce> findAll();

  @Override
  @EntityGraph(attributePaths = {"auteur"})
  Optional<Annonce> findById(Long id);
}
