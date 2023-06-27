package com.dnlkk.resistance.dto;

import com.dnlkk.resistance.objects.graph.ResistorMatrixWeightedGraph;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteVertexDTO {
    private ResistorMatrixWeightedGraph graph;
    private Integer vertex;
}
