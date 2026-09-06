package com.schoolmanagement.administration.emploi_temps.service;

import com.schoolmanagement.common.audit.JournaliserAutomatiquement;

import com.schoolmanagement.administration.emploi_temps.dto.request.ClasseRequest;
import com.schoolmanagement.administration.emploi_temps.dto.response.ClasseResponse;
import com.schoolmanagement.administration.emploi_temps.entity.Classe;
import com.schoolmanagement.administration.emploi_temps.repository.ClasseRepository;
import com.schoolmanagement.common.exception.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@JournaliserAutomatiquement
public class ClasseService {
  private final ClasseRepository classeRepository;

  @Transactional(readOnly = true)
  public List<ClasseResponse> obtenirToutesLesClasses() {
    return classeRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
  }

  @Transactional
  public ClasseResponse creerClasse(ClasseRequest request) {
    Classe classe =
        Classe.builder().nomClasse(request.getNomClasse()).niveau(request.getNiveau()).build();
    return mapToDto(classeRepository.save(classe));
  }

  @Transactional
  public ClasseResponse modifierClasse(Long id, ClasseRequest request) {
    Classe classe =
        classeRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Classe", id));
    classe.setNomClasse(request.getNomClasse());
    classe.setNiveau(request.getNiveau());
    return mapToDto(classeRepository.save(classe));
  }

  @Transactional
  public void supprimerClasse(Long id) {
    if (!classeRepository.existsById(id)) {
      throw new ResourceNotFoundException("Classe", id);
    }
    classeRepository.deleteById(id);
  }

  private ClasseResponse mapToDto(Classe c) {
    ClasseResponse dto = new ClasseResponse();
    dto.setId(c.getId());
    dto.setNomClasse(c.getNomClasse());
    dto.setNiveau(c.getNiveau());
    return dto;
  }
}
