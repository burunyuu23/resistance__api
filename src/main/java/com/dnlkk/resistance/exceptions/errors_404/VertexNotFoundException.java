package com.dnlkk.resistance.exceptions.errors_404;

import com.dnlkk.resistance.exceptions.AppException;
import org.springframework.http.HttpStatus;

public class VertexNotFoundException extends AppException {
    public VertexNotFoundException() {
        super("Vertex not found", HttpStatus.NOT_FOUND);
    }
}
