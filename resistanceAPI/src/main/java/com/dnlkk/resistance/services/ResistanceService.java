package com.dnlkk.resistance.services;

import com.dnlkk.resistance.dto.DeleteEdgeDTO;
import com.dnlkk.resistance.dto.DeleteEdgeResistorDTO;
import com.dnlkk.resistance.dto.DeleteVertexDTO;
import com.dnlkk.resistance.dto.GraphDTO;
import com.dnlkk.resistance.exceptions.VertexNotFoundException;
import com.dnlkk.resistance.objects.graph.Graph;
import com.dnlkk.resistance.objects.graph.ResistorWeightedGraph;
import com.dnlkk.resistance.objects.graph.WeightedGraph;
import com.dnlkk.resistance.objects.resistor.Resistor;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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

        graph.removeEdge(from, to);

        return new GraphDTO(graph.toString());
    }

    public GraphDTO deleteEdgeResistor(DeleteEdgeResistorDTO deleteEdgeResistorDTO) {
        ResistorWeightedGraph graph = deleteEdgeResistorDTO.getGraph();
        int from = deleteEdgeResistorDTO.getFrom();
        int to = deleteEdgeResistorDTO.getTo();
        String resistorName = deleteEdgeResistorDTO.getResistorName();

        graph.removeEdge(from, to, resistorName);
        System.out.println(graph);

        return new GraphDTO(graph.toString());
    }
}
