package com.example.simulation.forest.DTO;

import com.example.simulation.forest.Entity.GridCell;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GridDTO {
    private int height;
    private int width;
    private List<List<GridCell>> gridState;

    public GridDTO(int height, int width, List<List<GridCell>> gridState) {
        this.height = height;
        this.width = width;
        this.gridState = gridState;
    }
}
