package com.dnlkk.resistance.dto;

import com.dnlkk.resistance.objects.graph.ResistorMatrixWeightedGraph;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request on add resistor into edge with resistance")
public class AddResistorRequestDTO {
    @Schema(description = "The graph from string",
            implementation = String.class,
            example = "{0-1=[(R1:1), (R2:2), (R3:3), (R4:4)]};{0-2=[(R5:5)]};{0-3=[(R6:6), (R7:7), (R8:8)]};{1-2=[(R1:22)]};")
    private ResistorMatrixWeightedGraph graph;

    @Schema(description = "The starting vertex of the edge to be deleted",
            example = "0")
    private Integer from;

    @Schema(description = "The ending vertex of the edge to be deleted",
            example = "1")
    private Integer to;

    @Schema(description = "Resistance of resistor to be added",
            example = "10")
    private Integer resistance;
}
