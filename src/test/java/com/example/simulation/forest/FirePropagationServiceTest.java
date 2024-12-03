package com.example.simulation.forest;

import com.example.simulation.forest.Entity.CellState;
import com.example.simulation.forest.Entity.GridCell;
import com.example.simulation.forest.exception.SimulationException;
import com.example.simulation.forest.service.FirePropagationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FirePropagationServiceTest {

    private FirePropagationService firePropagationService;

    @BeforeEach
    void setUp() {
        firePropagationService = new FirePropagationService();
    }

    @Test
    void testInitializeGrid_withValidInput_shouldInitializeCorrectly() {
        int height = 3;
        int width = 3;
        List<int[]> initialFires = List.of(new int[]{0, 0}, new int[]{2, 2});

        List<List<GridCell>> grid = firePropagationService.initializeGrid(height, width, initialFires);

        assertEquals(3, grid.size());
        assertEquals(3, grid.get(0).size());
        assertEquals(CellState.BURNING, grid.get(0).get(0).getState());
        assertEquals(CellState.BURNING, grid.get(2).get(2).getState());
        assertEquals(CellState.TREE, grid.get(1).get(1).getState());
    }

    @Test
    void testInitializeGrid_withInvalidDimensions_shouldThrowException() {
        int height = -1;
        int width = 3;
        List<int[]> initialFires = List.of(new int[]{0, 0});

        assertThrows(SimulationException.class, () -> firePropagationService.initializeGrid(height, width, initialFires));
    }

    @Test
    void testPropagateFire_withValidInput_shouldSpreadFireCorrectly() {
        int height = 3;
        int width = 3;
        List<int[]> initialFires = List.of(new int[]{1, 1});
        List<List<GridCell>> grid = firePropagationService.initializeGrid(height, width, initialFires);

        List<List<GridCell>> result = firePropagationService.propagateFire(grid, 1.0); // Probabilité 100%

        assertEquals(CellState.ASH, result.get(1).get(1).getState());
        assertEquals(CellState.BURNING, result.get(0).get(1).getState());
        assertEquals(CellState.BURNING, result.get(2).get(1).getState());
    }

    @Test
    void testPropagateFire_withProbabilityZero_shouldNotSpreadFire() {
        int height = 3;
        int width = 3;
        List<int[]> initialFires = List.of(new int[]{1, 1});
        List<List<GridCell>> grid = firePropagationService.initializeGrid(height, width, initialFires);

        List<List<GridCell>> result = firePropagationService.propagateFire(grid, 0.0); // Probabilité 0%

        assertEquals(CellState.ASH, result.get(1).get(1).getState());
        assertEquals(CellState.TREE, result.get(0).get(1).getState());
        assertEquals(CellState.TREE, result.get(2).get(1).getState());
    }

    @Test
    void testPropagateFire_withInvalidProbability_shouldThrowException() {
        int height = 3;
        int width = 3;
        List<int[]> initialFires = List.of(new int[]{1, 1});
        List<List<GridCell>> grid = firePropagationService.initializeGrid(height, width, initialFires);

        assertThrows(SimulationException.class, () -> firePropagationService.propagateFire(grid, -0.1));
        assertThrows(SimulationException.class, () -> firePropagationService.propagateFire(grid, 1.1));
    }
}
