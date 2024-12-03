package com.example.simulation.forest.controller;

import com.example.simulation.forest.DTO.GridDTO;
import com.example.simulation.forest.Entity.GridCell;
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
    public GridDTO startSimulation(@RequestParam int height,
                                   @RequestParam int width,
                                   @RequestParam List<int[]> initialFires,
                                   @RequestParam double probability) {
        List<List<GridCell>> grid = firePropagationService.initializeGrid(height, width, initialFires);
        return GridMapper.toDTO(grid);
    }

    @PostMapping("/step")
    public GridDTO simulateStep(@RequestBody GridDTO currentGrid, @RequestParam double probability) {
        List<List<GridCell>> grid = firePropagationService.propagateFire(currentGrid.getGridState(), probability);
        return GridMapper.toDTO(grid);
    }
}
