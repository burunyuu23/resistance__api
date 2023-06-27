package com.dnlkk.resistance.dto;

import com.dnlkk.resistance.objects.graph.ResistorMatrixWeightedGraph;
import lombok.*;

@Data
public class DeleteEdgeResistorDTO {
    private String resistorName;
    private ResistorMatrixWeightedGraph graph;
    private Integer from;
    private Integer to;
}
