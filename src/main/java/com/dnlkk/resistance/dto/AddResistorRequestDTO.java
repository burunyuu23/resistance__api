package com.dnlkk.resistance.dto;

import com.dnlkk.resistance.objects.graph.ResistorMatrixWeightedGraph;
import lombok.Data;

@Data
public class AddResistorRequestDTO {
    private ResistorMatrixWeightedGraph graph;
    private Integer from;
    private Integer to;
    private Integer resistance;
}
