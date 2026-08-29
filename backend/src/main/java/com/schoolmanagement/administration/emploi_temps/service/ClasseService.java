package com.schoolmanagement.administration.emploi_temps.service;

import com.schoolmanagement.administration.emploi_temps.dto.request.ClasseRequest;
import com.schoolmanagement.administration.emploi_temps.dto.response.ClasseResponse;
import com.schoolmanagement.administration.emploi_temps.entity.Classe;
import com.schoolmanagement.administration.emploi_temps.repository.ClasseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClasseService {
    private final ClasseRepository classeRepository;

    @Transactional(readOnly = true)
    public List<ClasseResponse> obtenirToutesLesClasses() {
        return classeRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public ClasseResponse creerClasse(ClasseRequest request) {
        Classe classe = Classe.builder()
                .nomClasse(request.getNomClasse())
                .niveau(request.getNiveau())
                .build();
        return mapToDto(classeRepository.save(classe));
    }

    @Transactional
    public ClasseResponse modifierClasse(Long id, ClasseRequest request) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classe introuvable"));
        classe.setNomClasse(request.getNomClasse());
        classe.setNiveau(request.getNiveau());
        return mapToDto(classeRepository.save(classe));
    }

    @Transactional
    public void supprimerClasse(Long id) {
        if (!classeRepository.existsById(id)) {
            throw new RuntimeException("Classe introuvable");
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