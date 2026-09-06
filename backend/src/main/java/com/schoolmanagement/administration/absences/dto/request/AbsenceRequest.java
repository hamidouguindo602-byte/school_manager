package com.schoolmanagement.administration.absences.dto.request;

import java.time.LocalDate;
import lombok.Data;

@Data
public class AbsenceRequest {
  private Long eleveId;
  private Long emploiDuTempsId;
  private LocalDate dateAbsence;
}
