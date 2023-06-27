package com.dnlkk.resistance.exceptions;

import org.springframework.http.HttpStatus;

public class ResistorNotFoundException extends AppException{
    public ResistorNotFoundException() {
        super("Resistor not found", HttpStatus.NOT_FOUND);
    }
}
