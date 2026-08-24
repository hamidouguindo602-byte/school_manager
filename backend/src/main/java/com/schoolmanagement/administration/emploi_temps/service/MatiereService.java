package com.schoolmanagement.administration.emploi_temps.service;

import com.schoolmanagement.administration.emploi_temps.dto.request.MatiereRequest;
import com.schoolmanagement.administration.emploi_temps.dto.response.MatiereResponse;
import com.schoolmanagement.administration.emploi_temps.entity.Matiere;
import com.schoolmanagement.administration.emploi_temps.repository.MatiereRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatiereService {
    private final MatiereRepository matiereRepository;

    @Transactional(readOnly = true)
    public List<MatiereResponse> obtenirToutesLesMatieres() {
        return matiereRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public MatiereResponse creerMatiere(MatiereRequest request) {
        Matiere matiere = Matiere.builder()
                .nomMatiere(request.getNomMatiere())
                .coefficient(request.getCoefficient())
                .build();
        return mapToDto(matiereRepository.save(matiere));
    }

    @Transactional
    public MatiereResponse modifierMatiere(Long id, MatiereRequest request) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matière introuvable"));
        matiere.setNomMatiere(request.getNomMatiere());
        matiere.setCoefficient(request.getCoefficient());
        return mapToDto(matiereRepository.save(matiere));
    }

    @Transactional
    public void supprimerMatiere(Long id) {
        if (!matiereRepository.existsById(id)) {
            throw new RuntimeException("Matière introuvable");
        }
        matiereRepository.deleteById(id);
    }

    private MatiereResponse mapToDto(Matiere m) {
        MatiereResponse dto = new MatiereResponse();
        dto.setId(m.getId());
        dto.setNomMatiere(m.getNomMatiere());
        dto.setCoefficient(m.getCoefficient());
        return dto;
    }
}