package com.thiagosol.moviesbattle.entrypoint.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PlayerTokenResponseDTO(@Schema(description = "Token for bearer auth")
                                     String token) {
}
