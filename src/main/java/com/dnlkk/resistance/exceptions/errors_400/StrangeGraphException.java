package com.dnlkk.resistance.exceptions.errors_400;

import com.dnlkk.resistance.exceptions.AppException;
import org.springframework.http.HttpStatus;

public class StrangeGraphException extends AppException {
    public StrangeGraphException() {
        super("Strange graph", HttpStatus.BAD_REQUEST);
    }
}
