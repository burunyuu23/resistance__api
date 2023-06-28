package com.dnlkk.resistance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response in all ResistanceController's endpoints")
public class GraphResponseDTO {
    @Schema(description = "Graph in string representation",
            example = "{0-1=[(R1:1)]};{0-2=[(R2:2), (R3:3), (R4:4)]};"
    )
    private String graph;
}
