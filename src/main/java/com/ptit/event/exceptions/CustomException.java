package com.ptit.event.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomException extends RuntimeException {
    private final HttpStatus status;

    public CustomException(HttpStatus status, ErrorMessage message) {
        super(message.getMessage());
        this.status = status;
    }
}
