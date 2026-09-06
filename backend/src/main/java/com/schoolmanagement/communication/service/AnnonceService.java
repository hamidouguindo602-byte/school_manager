package com.schoolmanagement.communication.service;

import com.schoolmanagement.common.audit.JournaliserAutomatiquement;

import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.authentication.repository.UtilisateurRepository;
import com.schoolmanagement.common.exception.AuthentificationException;
import com.schoolmanagement.communication.dto.Response.AnnonceResponse;
import com.schoolmanagement.communication.dto.request.AnnonceRequest;
import com.schoolmanagement.communication.entity.Annonce;
import com.schoolmanagement.communication.repository.AnnonceRepository;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@JournaliserAutomatiquement
public class AnnonceService {

  private final AnnonceRepository annonceRepository;
  private final UtilisateurRepository utilisateurRepository;

  // =========================
  // LISTER
  // =========================

  public List<AnnonceResponse> findAll() {

    List<Annonce> annonces = annonceRepository.findAll();

    List<AnnonceResponse> responses = new ArrayList<>();

    for (Annonce annonce : annonces) {

      AnnonceResponse response = toResponse(annonce);

      responses.add(response);
    }

    return responses;
  }

  // =========================
  // CHERCHER PAR ID
  // =========================

  public AnnonceResponse findById(Long id) {

    Optional<Annonce> optionalAnnonce = annonceRepository.findById(id);

    if (optionalAnnonce.isEmpty()) {

      throw new EntityNotFoundException("Annonce introuvable avec l'id : " + id);
    }

    Annonce annonce = optionalAnnonce.get();

    return toResponse(annonce);
  }

  // =========================
  // CREER
  // =========================

  public AnnonceResponse create(AnnonceRequest request) {

    Annonce annonce = new Annonce();

    annonce.setTitre(request.titre());
    annonce.setContenu(request.contenu());

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null
        || !authentication.isAuthenticated()
        || !(authentication.getPrincipal() instanceof Long utilisateurId)) {
      throw new AuthentificationException(
          "Un utilisateur authentifié est requis pour créer une annonce");
    }

    Utilisateur auteur =
        utilisateurRepository
            .findById(utilisateurId)
            .orElseThrow(
                () -> new AuthentificationException("Utilisateur authentifié introuvable"));
    annonce.setAuteur(auteur);

    // Gestion du PDF
    MultipartFile fichier = request.fichier();

    if (fichier != null && !fichier.isEmpty()) {
      String nomOriginal = fichier.getOriginalFilename();

      if (nomOriginal == null || !nomOriginal.toLowerCase().endsWith(".pdf")) {
        throw new IllegalArgumentException("Seuls les fichiers PDF sont autorisés");
      }

      try {
        annonce.setNomFichier(nomOriginal);
        annonce.setFichierPdf(fichier.getBytes());

      } catch (IOException e) {
        throw new IllegalStateException("Erreur lors de l'enregistrement du fichier", e);
      }
    }

    Annonce annonceEnregistree = annonceRepository.save(annonce);

    return toResponse(annonceEnregistree);
  }

  // =========================
  // MODIFIER
  // =========================

  public AnnonceResponse update(Long id, AnnonceRequest request) {

    Optional<Annonce> optionalAnnonce = annonceRepository.findById(id);

    if (optionalAnnonce.isEmpty()) {

      throw new EntityNotFoundException("Annonce introuvable avec l'id : " + id);
    }

    Annonce annonce = optionalAnnonce.get();

    annonce.setTitre(request.titre());
    annonce.setContenu(request.contenu());

    MultipartFile fichier = request.fichier();

    if (fichier != null && !fichier.isEmpty()) {
      String nomOriginal = fichier.getOriginalFilename();

      if (nomOriginal == null || !nomOriginal.toLowerCase().endsWith(".pdf")) {
        throw new IllegalArgumentException("Seuls les fichiers PDF sont autorisés");
      }

      try {
        annonce.setNomFichier(nomOriginal);
        annonce.setFichierPdf(fichier.getBytes());

      } catch (IOException e) {
        throw new IllegalStateException("Erreur lors de la modification du fichier", e);
      }
    }

    Annonce annonceModifiee = annonceRepository.save(annonce);

    return toResponse(annonceModifiee);
  }

  // =========================
  // SUPPRIMER
  // =========================

  public void delete(Long id) {

    Optional<Annonce> optionalAnnonce = annonceRepository.findById(id);

    if (optionalAnnonce.isEmpty()) {

      throw new EntityNotFoundException("Annonce introuvable avec l'id : " + id);
    }

    Annonce annonce = optionalAnnonce.get();

    // Supprimer le PDF
    annonceRepository.delete(annonce);
  }

  // =========================
  // RECUPERER LE PDF
  // =========================

  public byte[] getFichier(Long id) {

    Optional<Annonce> optionalAnnonce = annonceRepository.findById(id);

    if (optionalAnnonce.isEmpty()) {

      throw new EntityNotFoundException("Annonce introuvable avec l'id : " + id);
    }

    Annonce annonce = optionalAnnonce.get();

    if (annonce.getFichierPdf() != null && annonce.getFichierPdf().length > 0) {
      return annonce.getFichierPdf();
    }

    /*if (annonce.getCheminFichier() == null) {
                throw new EntityNotFoundException(
                        "Cette annonce ne contient aucun fichier PDF"
                );
            }
    */
    throw new EntityNotFoundException("Fichier PDF introuvable");
  }

  // =========================
  // CONVERSION
  // =========================

  private AnnonceResponse toResponse(Annonce annonce) {

    Long idAuteur = null;

    if (annonce.getAuteur() != null) {
      idAuteur = annonce.getAuteur().getId();
    }

    return new AnnonceResponse(
        annonce.getIdAnnonce(),
        annonce.getTitre(),
        annonce.getContenu(),
        annonce.getDatePublication(),
        annonce.getNomFichier(),
        idAuteur);
  }
}
