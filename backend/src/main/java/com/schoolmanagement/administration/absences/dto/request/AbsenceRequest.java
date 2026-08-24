package com.schoolmanagement.administration.absences.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AbsenceRequest {
    private Long eleveId;
    private Long emploiDuTempsId;
    private LocalDate dateAbsence;
}