package com.example.simulation.forest.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class SimulationStepRequest {
    private List<List<GridCell>> grid;  // La grille actuelle
    private double probability;  // Probabilité de propagation du feu

}
