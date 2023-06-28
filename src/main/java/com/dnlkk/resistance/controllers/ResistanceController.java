package com.dnlkk.resistance.controllers;

import com.dnlkk.resistance.dto.AddResistorRequestDTO;
import com.dnlkk.resistance.dto.DeleteEdgeRequestDTO;
import com.dnlkk.resistance.dto.DeleteEdgeResistorRequestDTO;
import com.dnlkk.resistance.dto.DeleteVertexRequestDTO;
import com.dnlkk.resistance.exceptions.ResistorNotFoundException;
import com.dnlkk.resistance.exceptions.VertexNotFoundException;
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
    public ResponseEntity deleteVertex(@RequestBody DeleteVertexRequestDTO deleteVertexRequestDTO) {
        try {
            return ResponseEntity.ok(resistanceService.deleteVertex(deleteVertexRequestDTO));
        } catch (VertexNotFoundException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @DeleteMapping("/edge")
    public ResponseEntity deleteEdge(@RequestBody DeleteEdgeRequestDTO deleteEdgeRequestDTO) {
        try {
            return ResponseEntity.ok(resistanceService.deleteEdge(deleteEdgeRequestDTO));
        } catch (VertexNotFoundException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @DeleteMapping("/edge/resistor")
    public ResponseEntity deleteEdgeResistor(@RequestBody DeleteEdgeResistorRequestDTO deleteEdgeResistorRequestDTO) {
        try {
            return ResponseEntity.ok(resistanceService.deleteEdgeResistor(deleteEdgeResistorRequestDTO));
        } catch (ResistorNotFoundException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @PatchMapping("/edge/resistor")
    public ResponseEntity addEdgeResistor(@RequestBody AddResistorRequestDTO addResistorRequestDTO) {
        return ResponseEntity.ok(resistanceService.addEdgeResistor(addResistorRequestDTO));
    }
}
