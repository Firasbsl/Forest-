package com.example.simulation.forest.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StartSimulationRequest {
    private int height;
    private int width;
    private List<int[]> initialFires;
}
