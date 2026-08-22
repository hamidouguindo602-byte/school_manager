package com.schoolmanagement.authentication.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionRequest {
    private String nomPermission;
    private String description;
}
