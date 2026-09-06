package com.schoolmanagement.administration.emploi_temps.service;

import com.schoolmanagement.common.audit.JournaliserAutomatiquement;

import com.schoolmanagement.administration.emploi_temps.dto.request.MatiereRequest;
import com.schoolmanagement.administration.emploi_temps.dto.response.MatiereResponse;
import com.schoolmanagement.administration.emploi_temps.entity.Matiere;
import com.schoolmanagement.administration.emploi_temps.repository.MatiereRepository;
import com.schoolmanagement.common.exception.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@JournaliserAutomatiquement
public class MatiereService {
  private final MatiereRepository matiereRepository;

  @Transactional(readOnly = true)
  public List<MatiereResponse> obtenirToutesLesMatieres() {
    return matiereRepository.findAll().stream().map(this::mapToDto).toList();
  }

  @Transactional
  public MatiereResponse creerMatiere(MatiereRequest request) {
    Matiere matiere =
        Matiere.builder()
            .nomMatiere(request.getNomMatiere())
            .coefficient(request.getCoefficient())
            .code(request.getCode())
            .nom(request.getNom())
            .build();
    return mapToDto(matiereRepository.save(matiere));
  }

  @Transactional
  public MatiereResponse modifierMatiere(Long id, MatiereRequest request) {
    Matiere matiere =
        matiereRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Matière", id));
    matiere.setNomMatiere(request.getNomMatiere());
    matiere.setCoefficient(request.getCoefficient());
    matiere.setCode(request.getCode());
    matiere.setNom(request.getNom());
    return mapToDto(matiereRepository.save(matiere));
  }

  @Transactional
  public void supprimerMatiere(Long id) {
    if (!matiereRepository.existsById(id)) {
      throw new ResourceNotFoundException("Matière", id);
    }
    matiereRepository.deleteById(id);
  }

  private MatiereResponse mapToDto(Matiere matiere) {
    MatiereResponse dto = new MatiereResponse();
    dto.setId(matiere.getId());
    dto.setNomMatiere(matiere.getNomMatiere());
    dto.setCoefficient(matiere.getCoefficient());
    dto.setCode(matiere.getCode());
    dto.setNom(matiere.getNom());
    return dto;
  }
}
