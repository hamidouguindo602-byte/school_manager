package com.schoolmanagement.communication.dto.Response;

import java.time.LocalDate;

public record AnnonceResponse(
    Long idAnnonce,
    String titre,
    String contenu,
    LocalDate datePublication,
    String nomFichier,
    Long idAuteur) {}
