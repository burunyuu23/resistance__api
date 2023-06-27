package com.dnlkk.resistance.dto;

import com.dnlkk.resistance.objects.graph.ResistorMatrixWeightedGraph;
import com.dnlkk.resistance.objects.graph.ResistorWeightedGraph;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
public class DeleteEdgeResistorDTO {
    private String resistorName;
    private ResistorMatrixWeightedGraph graph;
    private Integer from;
    private Integer to;
}
