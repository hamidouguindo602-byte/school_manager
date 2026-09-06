package com.schoolmanagement.administration.absences.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AbsenceStatsResponse {
  private long total;
  private long justifiees;
  private long nonJustifiees;
  private double tauxAbsence;
}
