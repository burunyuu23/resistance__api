package com.dnlkk.resistance.controllers;

import com.dnlkk.resistance.dto.DeleteVertexDTO;
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
        return ResponseEntity.ok(resistanceService.deleteVertex(deleteVertexDTO));
    }
}
