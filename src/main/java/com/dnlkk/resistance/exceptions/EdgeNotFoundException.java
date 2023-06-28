package com.dnlkk.resistance.exceptions;

import org.springframework.http.HttpStatus;

public class EdgeNotFoundException extends AppException{
    public EdgeNotFoundException() {
        super("Edge not found", HttpStatus.NOT_FOUND);
    }
}
