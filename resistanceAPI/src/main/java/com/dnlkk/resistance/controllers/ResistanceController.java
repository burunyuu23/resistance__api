package com.dnlkk.resistance.controllers;

import com.dnlkk.resistance.dto.DeleteEdgeDTO;
import com.dnlkk.resistance.dto.DeleteEdgeResistorDTO;
import com.dnlkk.resistance.dto.DeleteVertexDTO;
import com.dnlkk.resistance.exceptions.ResistorNotFoundException;
import com.dnlkk.resistance.exceptions.VertexNotFoundException;
import com.dnlkk.resistance.objects.graph.Graph;
import com.dnlkk.resistance.objects.graph.ResistorMatrixWeightedGraph;
import com.dnlkk.resistance.objects.resistor.Resistor;
import com.dnlkk.resistance.services.ResistanceService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/resistance")
public class ResistanceController {

    private final ResistanceService resistanceService;

    @DeleteMapping("/vertex")
    public ResponseEntity deleteVertex(@RequestBody DeleteVertexDTO deleteVertexDTO) {
        try {
            return ResponseEntity.ok(resistanceService.deleteVertex(deleteVertexDTO));
        } catch (VertexNotFoundException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @DeleteMapping("/edge")
    public ResponseEntity deleteEdge(@RequestBody DeleteEdgeDTO deleteEdgeDTO) {
        return ResponseEntity.ok(resistanceService.deleteEdge(deleteEdgeDTO));
    }

    @DeleteMapping("/edge/resistor")
    public ResponseEntity deleteEdgeResistor(@RequestBody DeleteEdgeResistorDTO deleteEdgeResistorDTO) {
        try {
            return ResponseEntity.ok(resistanceService.deleteEdgeResistor(deleteEdgeResistorDTO));
        } catch (ResistorNotFoundException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
}
