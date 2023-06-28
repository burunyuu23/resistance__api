package com.dnlkk.resistance.dto.requests;

import com.dnlkk.resistance.objects.graph.ResistorMatrixWeightedGraph;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Request on delete vertex")
public class DeleteVertexRequestDTO {
    @Schema(
            description = "Graph from string",
            implementation = String.class,
            example = "{0-1=[(R1:1), (R2:2), (R3:3), (R4:4)]};{0-2=[(R5:5)]};{0-3=[(R6:6), (R7:7), (R8:8)]};{1-2=[(R1:22)]};"
    )
    private ResistorMatrixWeightedGraph graph;

    @Schema(
            description = "The vertex of the graph to be deleted",
            example = "1"
    )
    private Integer vertex;
}
