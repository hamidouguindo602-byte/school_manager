package com.schoolmanagement.authentication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionRequest {
  @NotBlank
  @Size(max = 100)
  private String nomPermission;

  @Size(max = 1000)
  private String description;
}
