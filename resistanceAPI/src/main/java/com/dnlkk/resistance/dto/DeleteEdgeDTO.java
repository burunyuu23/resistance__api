package com.dnlkk.resistance.dto;

import com.dnlkk.resistance.objects.graph.ResistorMatrixWeightedGraph;
import com.dnlkk.resistance.objects.graph.ResistorWeightedGraph;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
public class DeleteEdgeDTO {
    private ResistorMatrixWeightedGraph graph;
    private Integer from;
    private Integer to;
}
