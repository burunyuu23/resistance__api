package com.dnlkk.resistance.exceptions.errors_500;

import com.dnlkk.resistance.exceptions.AppException;
import org.springframework.http.HttpStatus;

public class ServerBadException extends AppException {
    public ServerBadException() {
        super("Server can't do this", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
