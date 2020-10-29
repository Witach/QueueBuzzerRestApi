package com.queuebuzzer.restapi.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class EntityDoesNotExistsControllerAdvice {

    @ExceptionHandler(EntityDoesNotExistsException.class)
    ResponseEntity<ApiError> handleEntityDoesNotExistsException(EntityDoesNotExistsException existsException) {
        var apiError = ApiError.builder()
                .message(existsException.getMessage())
                .build();
        return ResponseEntity.of(
                Optional.ofNullable(apiError)
        );
    }
}