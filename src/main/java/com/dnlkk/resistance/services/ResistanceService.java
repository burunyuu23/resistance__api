package com.dnlkk.resistance.services;

import com.dnlkk.resistance.dto.requests.*;
import com.dnlkk.resistance.dto.responses.CountTotalResistanceResponseDTO;
import com.dnlkk.resistance.dto.responses.CountTotalResistanceToMatrixApiResponseDTO;
import com.dnlkk.resistance.dto.responses.ErrorResponseDTO;
import com.dnlkk.resistance.dto.responses.GraphResponseDTO;
import com.dnlkk.resistance.exceptions.AppException;
import com.dnlkk.resistance.exceptions.errors_404.ResistorNotFoundException;
import com.dnlkk.resistance.exceptions.errors_404.VertexNotFoundException;
import com.dnlkk.resistance.exceptions.errors_500.ServerBadException;
import com.dnlkk.resistance.objects.graph.ResistorWeightedGraph;
import com.dnlkk.resistance.objects.graph.WeightedEdge;
import com.dnlkk.resistance.objects.graph.WeightedGraph;
import com.dnlkk.resistance.objects.resistor.Resistor;
import com.google.gson.Gson;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

@Service
@Data
public class ResistanceService {
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Value("${settings.url.matrixAPI.gauss}")
    private String url;

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

        while (graph.vertexCount() - 1 > to) {
            graph.removeVertex(graph.vertexCount() - 1);
        }

        double[][] matrix = new double[graph.edgeCount() + 1][graph.edgeCount() + 2];
        matrix[0][0] = 1;
        matrix[graph.vertexCount() - 1][matrix[0].length - 1] = eds;
        int k = graph.vertexCount();

        record SecondKirchhoffRuleHelpClass(int matrixRow, int finalVertex) {}
        List<SecondKirchhoffRuleHelpClass> secondKirchhoffRuleLoopList = new ArrayList<>();

        // Все входящие выходящие из узлов принимаем 1/-1
        // Берем из каждой проходки ровно одно сопротивление у всех узлов, что больше текущего
        // Если на одной грани больше чем один резистор, то берём отдельно пары первый/следующий (первый остаётся, следующий - итерируется)
        for (int i = 0; i < to - from; i++) {
            boolean currentVertexAddInMatrix = false;
            System.out.printf("From %d%n", i+from);

            int finalI = i;
            List<WeightedEdge<Resistor>> outputPaths =
                    StreamSupport
                            .stream(graph.adjacencyWithWeights(i+from).spliterator(),
                                    false)
                    .filter(resistorWeightedEdge ->
                            resistorWeightedEdge.to() > finalI + from)
                    .toList();

            System.out.printf("Size %d%n", outputPaths.size());
            if (outputPaths.size() > 1)
                for (int j = outputPaths.size() - 1; j > 0; j--) {
                    Resistor resistor = outputPaths.get(j).getWeight().get(0);
                    matrix[k][Integer.parseInt(resistor.getName().substring(1))] = resistor.getResistance();
                    secondKirchhoffRuleLoopList.add(
                            new SecondKirchhoffRuleHelpClass(k++, outputPaths.get(j).getTo())
                    );
                }

            int count = 0;
            for (WeightedEdge<Resistor> we : graph.adjacencyWithWeights(i+from)) {
                for (Resistor r : we.weight()) {
                    matrix[i][Integer.parseInt(r.getName().substring(1))] = we.to() < i+from ? 1 : -1;
                }

                if (we.to() > i+from) {
                    Resistor currentResistor = we.weight().get(0);

                    if (count == 0) {
                        for (SecondKirchhoffRuleHelpClass record : secondKirchhoffRuleLoopList) {
                            if (graph.isReachable(we.to(), record.finalVertex())) {
                                matrix[record.matrixRow()]
                                        [Integer.parseInt(currentResistor.getName().substring(1))]
                                        = -currentResistor.getResistance();
                            }
                        }

                        count++;
                    }

                    if (!currentVertexAddInMatrix) {
                        matrix[graph.vertexCount() - 1][Integer.parseInt(currentResistor.getName().substring(1))]
                                = currentResistor.getResistance();
                        currentVertexAddInMatrix = true;
                    }
                    for (int j = 1; j < we.weight().size(); j++) {
                        Resistor iterableResistor = we.weight().get(j);
                        matrix[k][Integer.parseInt(currentResistor.getName().substring(1))] = currentResistor.getResistance();
                        matrix[k][Integer.parseInt(iterableResistor.getName().substring(1))] = -iterableResistor.getResistance();
                        k++;
                    }

                }

                System.out.printf("Can go to %d%n", we.to());
                System.out.printf("%d have %s%n", we.to(), we.weight().toString());

//                {
//                    "graph" : "{0-1=[(R1:1), (R2:2), (R3:3), (R4:4)]};{0-2=[(R5:5)]};{0-3=[(R6:6), (R7:7), (R8:8)]};{1-2=[(R1:22)]};",
//                        "from" : 0,
//                        "to" : 2,
//                        "eds" : 1
//                }

//                {
//                    "graph" : "{0-1=[(R1:10)]};{1-2=[(R5:5),(R5:20)]};{1-3=[(R6:11)]};{2-3=[(R1:2)]};",
//                        "from" : 0,
//                        "to" : 3,
//                        "eds" : 1
//                }
            }
            System.out.println();
        }

        // Отослать double[][] на matrixAPI
        CountTotalResistanceToMatrixApiResponseDTO responseDTO = requestToMatrixAPI(matrix);

        List<Resistor> resistanceList = new ArrayList<>();
        graph.bfs(resistanceList::add);

        double[] amperage = responseDTO.getVariables();
        double[] voltage = resistanceList.stream()
                .map(resistor ->
                        resistor.getResistance()*amperage[Integer.parseInt(resistor.getName().substring(1))])
                .mapToDouble(Double::doubleValue)
                .toArray();
        double total_resistance = eds/amperage[0];

        return new CountTotalResistanceResponseDTO(voltage, amperage, total_resistance);
    }

    private CountTotalResistanceToMatrixApiResponseDTO requestToMatrixAPI(double[][] matrix) {
        // Создание экземпляра RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Заголовки запроса
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // Тело запроса
        CountTotalResistanceToMatrixApiRequestDTO requestBody
                = new CountTotalResistanceToMatrixApiRequestDTO();
        requestBody.setMatrix(matrix);
        HttpEntity<CountTotalResistanceToMatrixApiRequestDTO> requestEntity
                = new HttpEntity<>(requestBody, headers);

        // Выполнение запроса
        ResponseEntity<CountTotalResistanceToMatrixApiResponseDTO> responseEntity;
        try {
            responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    CountTotalResistanceToMatrixApiResponseDTO.class);
        } catch (HttpClientErrorException.BadRequest e) {
            throw Objects.requireNonNull(e.getResponseBodyAs(AppException.class));
        } catch (Exception e) {
            throw new ServerBadException();
        }
        // Обработка ответа
        return responseEntity.getBody();
    }
}
