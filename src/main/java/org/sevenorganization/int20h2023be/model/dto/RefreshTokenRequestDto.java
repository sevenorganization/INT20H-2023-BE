package org.sevenorganization.int20h2023be.model.dto;

import jakarta.validation.constraints.NotEmpty;

public record RefreshTokenRequestDto(
        @NotEmpty(message = "Refresh token is required.")
        String refreshToken) {
}
