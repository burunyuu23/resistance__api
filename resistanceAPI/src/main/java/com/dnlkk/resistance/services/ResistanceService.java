package com.dnlkk.resistance.services;

import com.dnlkk.resistance.dto.DeleteVertexDTO;
import com.dnlkk.resistance.dto.GraphDTO;
import com.dnlkk.resistance.objects.graph.Graph;
import com.dnlkk.resistance.objects.resistor.Resistor;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResistanceService {
    private final ModelMapper modelMapper;

    public GraphDTO deleteVertex(DeleteVertexDTO deleteVertexDTO) {
        Graph<Resistor> graph = deleteVertexDTO.getGraph();
        int vertex = deleteVertexDTO.getVertex();
        graph.removeVertex(vertex);

        return new GraphDTO(graph.toString());
    }
}
