package com.example.mazeapp.model;


import lombok.Getter;

import java.util.List;

@Getter
public class Maze {
    private final Cell[][] map;
    private final List<Cell> creationPath;
    private final Cell startCell;
    private final Cell exitCell;

    public Maze(Cell[][] map, List<Cell> creationPath, Cell startCell, Cell exitCell) {
        this.map = map;
        this.creationPath = creationPath;
        this.startCell = startCell;
        this.exitCell = exitCell;
    }

    public int getHeight() {
        return map.length;
    }

    public int getWidth() {
        return map[0].length;
    }

    public Cell getCell(int xIndex, int yIndex) {
        return this.map[xIndex][yIndex];
    }
}