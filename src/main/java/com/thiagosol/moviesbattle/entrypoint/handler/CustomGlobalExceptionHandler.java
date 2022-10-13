package com.thiagosol.moviesbattle.entrypoint.handler;

import com.thiagosol.moviesbattle.entrypoint.dto.ApiErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleException(RuntimeException runtimeException){
        ApiErrorResponseDTO apiError = new ApiErrorResponseDTO(runtimeException);
        return ResponseEntity.status(apiError.code()).body(apiError);
    }
}
