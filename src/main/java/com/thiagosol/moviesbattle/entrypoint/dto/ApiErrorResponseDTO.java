package com.thiagosol.moviesbattle.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ApiErrorResponseDTO(@Schema(description = "Error Message")
                                  String message) {

    public ApiErrorResponseDTO(RuntimeException runtimeException){
        this(runtimeException.getMessage());
    }
}