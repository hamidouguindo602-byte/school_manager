package com.schoolmanagement.administration.evaluations.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class NoteResponse {
    private Long id;
    private Float valeur;
    private LocalDate dateSaisie;
    private Long idEvaluation;
    private Long idEleve;
}