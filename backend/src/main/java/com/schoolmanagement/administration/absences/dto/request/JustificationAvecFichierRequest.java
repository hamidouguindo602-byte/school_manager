package com.schoolmanagement.administration.absences.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class JustificationAvecFichierRequest {
  private MultipartFile documentJustificatif;
}
