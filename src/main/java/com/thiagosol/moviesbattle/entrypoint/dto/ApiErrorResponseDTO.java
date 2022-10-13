package com.thiagosol.moviesbattle.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ApiErrorResponseDTO(String message, int code) {

    public ApiErrorResponseDTO(RuntimeException runtimeException){
        this(runtimeException.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
}