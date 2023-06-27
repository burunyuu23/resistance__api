package com.dnlkk.resistance.dto;

import com.dnlkk.resistance.objects.graph.ResistorMatrixWeightedGraph;
import lombok.Data;

@Data
public class DeleteEdgeDTO {
    private ResistorMatrixWeightedGraph graph;
    private Integer from;
    private Integer to;
}
