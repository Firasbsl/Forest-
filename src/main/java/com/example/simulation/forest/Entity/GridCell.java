package com.example.simulation.forest.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GridCell {
    private int x;
    private int y;
    private CellState state;

    public GridCell(int x, int y, CellState state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }
}
