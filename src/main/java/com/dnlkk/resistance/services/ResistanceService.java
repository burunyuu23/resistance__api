package com.dnlkk.resistance.services;

import com.dnlkk.resistance.dto.*;
import com.dnlkk.resistance.exceptions.VertexNotFoundException;
import com.dnlkk.resistance.objects.graph.ResistorWeightedGraph;
import com.dnlkk.resistance.objects.graph.WeightedGraph;
import com.dnlkk.resistance.objects.resistor.Resistor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResistanceService {

    public GraphDTO deleteVertex(DeleteVertexDTO deleteVertexDTO) {
        WeightedGraph<Resistor> graph = deleteVertexDTO.getGraph();
        int vertex = deleteVertexDTO.getVertex();

        try {
            graph.removeVertex(vertex);
        } catch (Exception e) {
            throw new VertexNotFoundException();
        }

        return new GraphDTO(graph.toString());
    }

    public GraphDTO deleteEdge(DeleteEdgeDTO deleteEdgeDTO) {
        WeightedGraph<Resistor> graph = deleteEdgeDTO.getGraph();
        int from = deleteEdgeDTO.getFrom();
        int to = deleteEdgeDTO.getTo();

        try {
            graph.removeEdge(from, to);
        } catch (Exception e) {
            throw new VertexNotFoundException();
        }

        return new GraphDTO(graph.toString());
    }

    public GraphDTO deleteEdgeResistor(DeleteEdgeResistorDTO deleteEdgeResistorDTO) {
        ResistorWeightedGraph graph = deleteEdgeResistorDTO.getGraph();
        int from = deleteEdgeResistorDTO.getFrom();
        int to = deleteEdgeResistorDTO.getTo();
        String resistorName = deleteEdgeResistorDTO.getResistorName();

        graph.removeEdge(from, to, resistorName);

        return new GraphDTO(graph.toString());
    }

    public GraphDTO addEdgeResistor(AddResistorDTO addResistorDTO) {
        ResistorWeightedGraph graph = addResistorDTO.getGraph();
        int from = addResistorDTO.getFrom();
        int to = addResistorDTO.getTo();
        int resistance = addResistorDTO.getResistance();

        graph.addEdge(from, to, new Resistor("R", resistance));

        return new GraphDTO(graph.toString());
    }
}
