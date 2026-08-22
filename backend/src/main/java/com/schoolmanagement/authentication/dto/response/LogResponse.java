package com.schoolmanagement.authentication.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogResponse {
    private Long id;
    private String action;
    private LocalDateTime dateAction;
}
