package org.sevenorganization.int20h2023be.model.dto;

import jakarta.validation.constraints.NotNull;

public record SignUpRequestDto(
        String email,
        String password,
        String firstName,
        String lastName,
        @NotNull
        String authProvider
) {
}
