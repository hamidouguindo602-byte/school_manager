package com.schoolmanagement.administration.absences.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class BatchJustificationRequest {
  @NotNull @NotEmpty private List<Long> absenceIds;

  private String motif;
}
