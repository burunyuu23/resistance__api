package com.dnlkk.resistance.controllers;

import com.dnlkk.resistance.dto.*;
import com.dnlkk.resistance.exceptions.AppException;
import com.dnlkk.resistance.services.ResistanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/resistance")
@Tag(name = "Resistance", description = "Methods of working with the resistance graph")
public class ResistanceController {

    private final ResistanceService resistanceService;

    @Operation(summary = "Remove vertex from graph")
    @ApiResponse(
            responseCode = "200",
            description = "Operation succeeded",
            content = @Content(
                    schema = @Schema(
                            implementation = GraphResponseDTO.class,
                            example = "{0-1=[(R1:5)]};{0-2=[(R2:6), (R3:7), (R4:8)]};"
                    )))
    @ApiResponse(
            responseCode = "404",
            description = "Vertex with current index not found in graph",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponseDTO.class
                    )))
    @DeleteMapping("/vertex")
    public ResponseEntity<GraphResponseDTO> deleteVertex(@RequestBody DeleteVertexRequestDTO deleteVertexRequestDTO) {
        return ResponseEntity.ok(resistanceService.deleteVertex(deleteVertexRequestDTO));
    }

    @Operation(summary = "Remove edge between 'from' and 'to' vertexes in graph")
    @ApiResponse(
            responseCode = "200",
            description = "Operation succeeded",
            content = @Content(
                    schema = @Schema(
                            implementation = GraphResponseDTO.class,
                            example = "{0-1=[(R2:2), (R3:3), (R4:4)]};{0-2=[(R5:5)]};{0-3=[(R6:6), (R7:7), (R8:8)]};{1-2=[(R1:22)]};"
                    )))
    @ApiResponse(
            responseCode = "404",
            description = "Edge between 'from' and 'to' vertexes not found in graph",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponseDTO.class
                    )))
    @DeleteMapping("/edge")
    public ResponseEntity<GraphResponseDTO> deleteEdge(@RequestBody DeleteEdgeRequestDTO deleteEdgeRequestDTO) {
        return ResponseEntity.ok(resistanceService.deleteEdge(deleteEdgeRequestDTO));
    }

    @DeleteMapping("/edge/resistor")
    @Operation(
            summary = "Remove resistor from edge between 'from' and 'to' vertexes in graph"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Operation succeeded",
            content = @Content(
                    schema = @Schema(
                            implementation = GraphResponseDTO.class,
                            example = "{0-1=[(R2:2), (R3:3), (R4:4)]};{0-2=[(R5:5)]};{0-3=[(R6:6), (R7:7), (R8:8)]};{1-2=[(R1:22)]};"
                    )))
    @ApiResponse(
            responseCode = "404",
            description = "Resistor with name 'R*' not found in edge or edge between 'from' and 'to' vertexes not found in graph",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponseDTO.class
                    )))
    public ResponseEntity<GraphResponseDTO> deleteEdgeResistor(@RequestBody DeleteEdgeResistorRequestDTO deleteEdgeResistorRequestDTO) {
        return ResponseEntity.ok(resistanceService.deleteEdgeResistor(deleteEdgeResistorRequestDTO));
    }

    @PatchMapping("/edge/resistor")
    public ResponseEntity<GraphResponseDTO> addEdgeResistor(@RequestBody AddResistorRequestDTO addResistorRequestDTO) {
        return ResponseEntity.ok(resistanceService.addEdgeResistor(addResistorRequestDTO));
    }
}
