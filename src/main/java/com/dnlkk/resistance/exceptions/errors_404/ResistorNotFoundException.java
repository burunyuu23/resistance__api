package com.dnlkk.resistance.exceptions.errors_404;

import com.dnlkk.resistance.exceptions.AppException;
import org.springframework.http.HttpStatus;

public class ResistorNotFoundException extends AppException {
    public ResistorNotFoundException() {
        super("Resistor not found", HttpStatus.NOT_FOUND);
    }
}
