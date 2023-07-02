package com.dnlkk.resistance.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request on matrix API from count total resistance")
public class CountTotalResistanceToMatrixApiRequestDTO {
    @Schema(description = "Matrix with first and second Kirchhoff rules ",
            example = "{{1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, " +
                    "{0.0, 1.0, -1.0, -1.0, 0.0, 0.0, 0.0, 0.0}, " +
                    "{0.0, 0.0, 1.0, 0.0, -1.0, -1.0, -1.0, 0.0}, " +
                    "{0.0, 12.0, 23.0, 0.0, 4.0, 0.0, 0.0, 1.0}, " +
                    "{0.0, 0.0, -23.0, 2.0, -4.0, 0.0, 0.0, 0.0}, " +
                    "{0.0, 0.0, 0.0, 0.0, 4.0, -5.0, 0.0, 0.0}, " +
                    "{0.0, 0.0, 0.0, 0.0, 4.0, 0.0, -6.0, 0.0}}")
    private double[][] matrix;
}
