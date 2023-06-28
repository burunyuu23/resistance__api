package com.dnlkk.resistance.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response on count total resistance")
public class CountTotalResistanceResponseDTO {
    @Schema(
            description = "Voltage on each resistor",
            example = "{1.5, 2.0, 3.0, 1.0}")
    private double[] voltage;
    @Schema(
            description = "Amperage on each resistor",
            example = "{1.0 1.0 1.0 1.0}")
    private double[] amperage;
    @Schema(
            description = "Total resistance on graph (between 'from' and 'to' vertexes)",
            example = "13.8497461928934")
    private double totalResistance;
}
