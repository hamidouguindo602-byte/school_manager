package com.schoolmanagement.administration.gestion_scolaire.service;

import com.schoolmanagement.administration.gestion_scolaire.dto.request.InscriptionRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.InscriptionResponse;
import com.schoolmanagement.administration.gestion_scolaire.entity.*;
import com.schoolmanagement.administration.gestion_scolaire.repository.ClasseRepository;
import com.schoolmanagement.administration.gestion_scolaire.repository.EleveRepository;
import com.schoolmanagement.administration.gestion_scolaire.repository.FormationRepository;
import com.schoolmanagement.administration.gestion_scolaire.repository.InscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;
    private final EleveRepository eleveRepository;
    private final FormationRepository formationRepository;
    private final ClasseRepository classeRepository;

    /**
     * Crée une nouvelle inscription en exigeant un statut valide parmi l'Enum.
     */
    public InscriptionResponse creerInscription(InscriptionRequest request) {
        Eleve eleve = eleveRepository.findById(request.getIdEleve())
                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec l'ID : " + request.getIdEleve()));

        Formation formation = formationRepository.findById(request.getIdFormation())
                .orElseThrow(() -> new RuntimeException("Formation non trouvée avec l'ID : " + request.getIdFormation()));

        Classe classe = classeRepository.findById(request.getIdClasse())
                .orElseThrow(() -> new RuntimeException("Classe non trouvée avec l'ID : " + request.getIdClasse()));

        Inscription inscription = Inscription.builder()
                .eleve(eleve)
                .formation(formation)
                .classe(classe)
                .dateInscription(request.getDateInscription() != null ? request.getDateInscription() : LocalDate.now())
                .statut(request.getStatut()) // Récupère le statut validé depuis le DTO (en_attente, valide, annule)
                .build();

        Inscription saved = inscriptionRepository.save(inscription);
        return mapToResponse(saved);
    }

    /**
     * Valide une inscription existante (Transition vers 'valide')
     */
    public InscriptionResponse validerInscription(Long id) {
        Inscription inscription = inscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée avec l'ID : " + id));

        inscription.setStatut(StatusInscription.valide);
        Inscription updated = inscriptionRepository.save(inscription);
        return mapToResponse(updated);
    }

    /**
     * Annule une inscription existante (Transition vers 'annule')
     */
    public InscriptionResponse annulerInscription(Long id) {
        Inscription inscription = inscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée avec l'ID : " + id));

        inscription.setStatut(StatusInscription.annule);
        Inscription updated = inscriptionRepository.save(inscription);
        return mapToResponse(updated);
    }

    /**
     * Récupère la liste de toutes les inscriptions
     */
    public List<InscriptionResponse> listerToutesLesInscriptions() {
        return inscriptionRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une inscription spécifique par son ID
     */
    public InscriptionResponse recupererParId(Long id) {
        Inscription inscription = inscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée avec l'ID : " + id));
        return mapToResponse(inscription);
    }

    /**
     * Méthode utilitaire de mapping de l'entité vers le DTO Response
     */
    private InscriptionResponse mapToResponse(Inscription inscription) {
        return InscriptionResponse.builder()
                .idInscription(inscription.getId()) // ID hérité de EntieBase
                .idEleve(inscription.getEleve().getIdEleve())
                .idFormation(inscription.getFormation().getIdFormation())
                .idClasse(inscription.getClasse().getIdClasse())
                .dateInscription(inscription.getDateInscription())
                .statut(inscription.getStatut())
                .build();
    }
}