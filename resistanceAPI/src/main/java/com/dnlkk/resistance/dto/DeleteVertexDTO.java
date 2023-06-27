package com.dnlkk.resistance.dto;

import com.dnlkk.resistance.objects.graph.ResistorMatrixWeightedGraph;
import com.dnlkk.resistance.objects.graph.ResistorWeightedGraph;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class DeleteVertexDTO {
    private ResistorMatrixWeightedGraph graph;
    private Integer vertex;
}
