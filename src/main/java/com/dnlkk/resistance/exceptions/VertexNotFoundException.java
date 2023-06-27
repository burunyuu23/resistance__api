package com.dnlkk.resistance.exceptions;

import org.springframework.http.HttpStatus;

public class VertexNotFoundException extends AppException{
    public VertexNotFoundException() {
        super("Vertex not found", HttpStatus.NOT_FOUND);
    }
}
