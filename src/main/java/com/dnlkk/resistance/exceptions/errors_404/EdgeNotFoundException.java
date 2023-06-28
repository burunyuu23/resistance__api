package com.dnlkk.resistance.exceptions.errors_404;

import com.dnlkk.resistance.exceptions.AppException;
import org.springframework.http.HttpStatus;

public class EdgeNotFoundException extends AppException {
    public EdgeNotFoundException() {
        super("Edge not found", HttpStatus.NOT_FOUND);
    }
}
