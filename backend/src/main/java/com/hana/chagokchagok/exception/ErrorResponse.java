package com.hana.chagokchagok.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorType errorType) {
        return ResponseEntity
                .status(errorType.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorType.getHttpStatus().value())
                        .error(errorType.getHttpStatus().name())
                        .code(errorType.name())
                        .message(errorType.getMessage())
                        .build()
                );
    }
}
