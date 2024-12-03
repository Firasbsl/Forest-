package com.example.simulation.forest.service;

import com.example.simulation.forest.Entity.CellState;
import com.example.simulation.forest.Entity.GridCell;
import com.example.simulation.forest.exception.SimulationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class FirePropagationService {
    private final Logger logger = LoggerFactory.getLogger(FirePropagationService.class);
    private final Random random = new Random();

    public List<List<GridCell>> initializeGrid(int height, int width, List<int[]> initialFires) {
             logger.info("Initialisation de la grille avec dimensions {}x{}", height, width);


            if (height <= 0 || width <= 0) {
            throw new SimulationException("Les dimensions de la grille doivent être positives.");
        }

        List<List<GridCell>> grid = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            List<GridCell> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(new GridCell(i, j, CellState.TREE));
            }
            grid.add(row);
        }

        for (int[] position : initialFires) {
            if (position[0] < 0 || position[0] >= height || position[1] < 0 || position[1] >= width) {
                throw new SimulationException("Position initiale en dehors des limites de la grille.");
            }
            grid.get(position[0]).get(position[1]).setState(CellState.BURNING);
        }
        logger.info("Grille initialisée avec {} positions en feu.", initialFires.size());


        return grid;
    }

    public List<List<GridCell>> propagateFire(List<List<GridCell>> grid, double probability) {
        logger.info("Propagation du feu avec une probabilité de {}", probability);

        if (probability < 0 || probability > 1) {
            throw new SimulationException("La probabilité doit être comprise entre 0 et 1.");
        }

        int height = grid.size();
        int width = grid.get(0).size();

        List<int[]> newFires = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                GridCell cell = grid.get(i).get(j);
                if (cell.getState() == CellState.BURNING) {
                    cell.setState(CellState.ASH);
                    spreadFire(grid, i, j, probability, newFires);
                }
            }
        }
        logger.info("{} nouvelles cases en feu après propagation.", newFires.size());
        for (int[] fire : newFires) {
            grid.get(fire[0]).get(fire[1]).setState(CellState.BURNING);
        }

        return grid;
    }

    private void spreadFire(List<List<GridCell>> grid, int x, int y, double probability, List<int[]> newFires) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            if (isValid(grid, newX, newY) && grid.get(newX).get(newY).getState() == CellState.TREE) {
                if (random.nextDouble() < probability) {
                    newFires.add(new int[]{newX, newY});
                }
            }
        }
    }

    private boolean isValid(List<List<GridCell>> grid, int x, int y) {
        return x >= 0 && x < grid.size() && y >= 0 && y < grid.get(0).size();
    }
}
