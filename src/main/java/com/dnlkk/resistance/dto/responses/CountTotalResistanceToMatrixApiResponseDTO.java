package com.dnlkk.resistance.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response from matrix API on count total resistance")
public class CountTotalResistanceToMatrixApiResponseDTO {

    @Schema(description = "Amperage on each of the resistors",
            example = "{0.07220348922445388, 0.07220348922445388, 0.06677906465327664, 0.005424424571177247, 0.0014660606949127692, 0.001759272833895323, 0.0021990910423691544}")
    private double[] variables;
}
