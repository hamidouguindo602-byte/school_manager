package com.schoolmanagement.authentication.dto.response;

import com.schoolmanagement.authentication.entity.TypeRole;

public record ConnexionResponse(
        String token,
        TypeRole role

) {
}
