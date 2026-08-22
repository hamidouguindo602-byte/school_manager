package com.schoolmanagement.authentication.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogRequest {
    private String action;
    private LocalDateTime dateAction;
}
