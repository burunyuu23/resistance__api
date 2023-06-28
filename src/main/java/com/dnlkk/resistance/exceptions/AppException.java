package com.dnlkk.resistance.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
public class AppException extends RuntimeException{
    @EqualsAndHashCode.Include
    private final HttpStatus status;

    public AppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public String getMessage(){
        return super.getMessage();
    }
}
