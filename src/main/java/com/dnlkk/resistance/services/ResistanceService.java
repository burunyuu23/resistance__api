package com.dnlkk.resistance.services;

import com.dnlkk.resistance.dto.requests.*;
import com.dnlkk.resistance.dto.responses.CountTotalResistanceResponseDTO;
import com.dnlkk.resistance.dto.responses.GraphResponseDTO;
import com.dnlkk.resistance.exceptions.errors_404.ResistorNotFoundException;
import com.dnlkk.resistance.exceptions.errors_404.VertexNotFoundException;
import com.dnlkk.resistance.objects.graph.ResistorWeightedGraph;
import com.dnlkk.resistance.objects.graph.WeightedGraph;
import com.dnlkk.resistance.objects.resistor.Resistor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Data
public class ResistanceService {
    private final ModelMapper modelMapper;

    public GraphResponseDTO deleteVertex(DeleteVertexRequestDTO deleteVertexRequestDTO) {
        WeightedGraph<Resistor> graph = deleteVertexRequestDTO.getGraph();
        int vertex = deleteVertexRequestDTO.getVertex();

        try {
            graph.removeVertex(vertex);
        } catch (Exception e) {
            throw new VertexNotFoundException();
        }

        return modelMapper.map(graph, GraphResponseDTO.class);
    }

    public GraphResponseDTO deleteEdge(DeleteEdgeRequestDTO deleteEdgeRequestDTO) {
        WeightedGraph<Resistor> graph = deleteEdgeRequestDTO.getGraph();
        int from = deleteEdgeRequestDTO.getFrom();
        int to = deleteEdgeRequestDTO.getTo();

        try {
            graph.removeEdge(from, to);
        } catch (Exception e) {
            throw new VertexNotFoundException();
        }

        System.out.println(graph);
        System.out.println(modelMapper.map(graph, GraphResponseDTO.class));

        return modelMapper.map(graph, GraphResponseDTO.class);
    }

    public GraphResponseDTO deleteEdgeResistor(DeleteEdgeResistorRequestDTO deleteEdgeResistorRequestDTO) {
        ResistorWeightedGraph graph = deleteEdgeResistorRequestDTO.getGraph();
        int from = deleteEdgeResistorRequestDTO.getFrom();
        int to = deleteEdgeResistorRequestDTO.getTo();
        String resistorName = deleteEdgeResistorRequestDTO.getResistorName();

        try {
            graph.removeEdge(from, to, resistorName);
        } catch (ResistorNotFoundException e) {
            throw new ResistorNotFoundException();
        } catch (Exception e) {
            throw new VertexNotFoundException();
        }

        return modelMapper.map(graph, GraphResponseDTO.class);
    }

    public GraphResponseDTO addEdgeResistor(AddResistorRequestDTO addResistorRequestDTO) {
        ResistorWeightedGraph graph = addResistorRequestDTO.getGraph();
        int from = addResistorRequestDTO.getFrom();
        int to = addResistorRequestDTO.getTo();
        int resistance = addResistorRequestDTO.getResistance();

        try {
            graph.addEdge(from, to, new Resistor("R", resistance));
        } catch (Exception e) {
            throw new VertexNotFoundException();
        }

        return modelMapper.map(graph, GraphResponseDTO.class);
    }

    public CountTotalResistanceResponseDTO countTotalResistance(CountTotalResistanceRequestDTO countTotalResistanceRequestDTO) {
        ResistorWeightedGraph graph = countTotalResistanceRequestDTO.getGraph();
        int from = countTotalResistanceRequestDTO.getFrom();
        int to = countTotalResistanceRequestDTO.getTo();
        double eds = countTotalResistanceRequestDTO.getEds();


        // Отослать double[][] на matrixAPI

        return modelMapper.map(null, CountTotalResistanceResponseDTO.class);
    }
}
