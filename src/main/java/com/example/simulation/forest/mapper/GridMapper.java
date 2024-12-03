package com.example.simulation.forest.mapper;

import com.example.simulation.forest.DTO.GridDTO;
import com.example.simulation.forest.Entity.GridCell;

import java.util.List;
import java.util.stream.Collectors;

public class GridMapper {
    public static GridDTO toDTO(List<List<GridCell>> grid) {
        List<List<String>> gridState = grid.stream()
                .map(row -> row.stream()
                        .map(cell -> cell.getState().name())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        return new GridDTO(grid.size(), grid.get(0).size(), gridState);
    }
}
