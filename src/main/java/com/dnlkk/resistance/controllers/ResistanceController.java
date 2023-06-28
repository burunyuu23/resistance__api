package com.dnlkk.resistance.controllers;

import com.dnlkk.resistance.dto.*;
import com.dnlkk.resistance.services.ResistanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/resistance")
public class ResistanceController {

    private final ResistanceService resistanceService;

    @DeleteMapping("/vertex")
    public ResponseEntity<GraphResponseDTO> deleteVertex(@RequestBody DeleteVertexRequestDTO deleteVertexRequestDTO) {
        return ResponseEntity.ok(resistanceService.deleteVertex(deleteVertexRequestDTO));
    }

    @DeleteMapping("/edge")
    public ResponseEntity<GraphResponseDTO> deleteEdge(@RequestBody DeleteEdgeRequestDTO deleteEdgeRequestDTO) {
        return ResponseEntity.ok(resistanceService.deleteEdge(deleteEdgeRequestDTO));
    }

    @DeleteMapping("/edge/resistor")
    public ResponseEntity<GraphResponseDTO> deleteEdgeResistor(@RequestBody DeleteEdgeResistorRequestDTO deleteEdgeResistorRequestDTO) {
        return ResponseEntity.ok(resistanceService.deleteEdgeResistor(deleteEdgeResistorRequestDTO));
    }

    @PatchMapping("/edge/resistor")
    public ResponseEntity<GraphResponseDTO> addEdgeResistor(@RequestBody AddResistorRequestDTO addResistorRequestDTO) {
        return ResponseEntity.ok(resistanceService.addEdgeResistor(addResistorRequestDTO));
    }
}
