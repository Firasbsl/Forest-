package com.example.simulation.forest.controller;

import com.example.simulation.forest.DTO.GridDTO;
import com.example.simulation.forest.Entity.GridCell;
import com.example.simulation.forest.Entity.SimulationStepRequest;
import com.example.simulation.forest.Entity.StartSimulationRequest;
import com.example.simulation.forest.mapper.GridMapper;
import com.example.simulation.forest.service.FirePropagationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/simulation")
public class SimulationController {
    private final FirePropagationService firePropagationService;

    public SimulationController(FirePropagationService firePropagationService) {
        this.firePropagationService = firePropagationService;
    }

    @PostMapping("/start")
    public GridDTO startSimulation(@RequestBody StartSimulationRequest request) {
        // Initialisation de la grille à partir des dimensions et des positions des feux
        List<List<GridCell>> grid = firePropagationService.initializeGrid(
                request.getHeight(),
                request.getWidth(),
                request.getInitialFires()
        );
        return GridMapper.toDTO(grid);
    }

    @PostMapping("/step")
    public GridDTO simulateStep(@RequestBody SimulationStepRequest request) {
        // Simulation d'une étape de propagation du feu
        List<List<GridCell>> grid = firePropagationService.propagateFire(
                request.getGrid(),
                request.getProbability()
        );
        return GridMapper.toDTO(grid);
    }
}
