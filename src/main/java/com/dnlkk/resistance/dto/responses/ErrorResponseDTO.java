package com.dnlkk.resistance.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Schema(description = "Response with error")
public class ErrorResponseDTO {
    @Schema(
            description = "Error message",
            example = "Something not found")
    private String message;

    @Schema(
            description = "Error status",
            example = "404 NOT_FOUND")
    private HttpStatus status;
}
