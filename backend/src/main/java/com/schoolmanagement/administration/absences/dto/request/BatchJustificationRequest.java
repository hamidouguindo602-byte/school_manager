package com.schoolmanagement.administration.absences.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class BatchJustificationRequest {
    @NotNull
    @NotEmpty
    private List<Long> absenceIds;

    private String motif;
}