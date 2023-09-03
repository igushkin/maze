package com.example.mazeapp.util;

import com.example.mazeapp.model.Cell;
import com.example.mazeapp.model.Maze;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MapGenerator {
    private final int height;
    private final int width;
    private final Cell[][] map;
    private final Set<Cell> visitHistory;

    public MapGenerator(int height, int width) {
        if (height <= 2 || height % 2 == 0) {
            throw new InvalidParameterException(String.format("Height must be an odd number, but received %s", height));
        }
        if (width <= 2 || width % 2 == 0) {
            throw new InvalidParameterException(String.format("Width must be an odd number, but received %s", width));
        }

        this.map = new Cell[height][width];
        this.height = height;
        this.width = width;
        this.visitHistory = new LinkedHashSet<>();
    }

    public Maze generateMap() {
        var map = preFillMap(this.map);
        map = generateMap(map, map[1][1]);

        var startCell = map[1][1];
        var exitCell = map[height - 2][width - 1];
        exitCell.setCanBeVisited(true);
        visitHistory.add(exitCell);

        return new Maze(map,
                visitHistory.stream().collect(Collectors.toList()),
                startCell,
                exitCell
        );
    }

    private Cell[][] generateMap(Cell[][] map, Cell cell) {
        visitHistory.add(cell);

        while (true) {
            var nextCell = getNextCell(map, cell);
            if (nextCell == null) {
                return map;
            } else {
                createPassage(map, cell, nextCell);
                generateMap(map, nextCell);
            }
        }
    }

    private Cell[][] createPassage(Cell[][] map, Cell cell1, Cell cell2) {
        int xIndex = cell1.getXIndex() == cell2.getXIndex() ? cell1.getXIndex() : Math.max(cell1.getXIndex(), cell2.getXIndex()) - 1;
        int yIndex = cell1.getYIndex() == cell2.getYIndex() ? cell1.getYIndex() : Math.max(cell1.getYIndex(), cell2.getYIndex()) - 1;
        var cell = map[xIndex][yIndex];
        cell.setCanBeVisited(true);
        visitHistory.add(cell);
        return map;
    }

    private Cell[][] preFillMap(Cell[][] map) {
        for (int xIndex = 0; xIndex < height; xIndex++) {
            for (int yIndex = 0; yIndex < width; yIndex++) {
                if (xIndex % 2 == 0 || yIndex % 2 == 0) {
                    map[xIndex][yIndex] = new Cell(false, xIndex, yIndex);
                } else {
                    map[xIndex][yIndex] = new Cell(true, xIndex, yIndex);
                }
            }
        }
        return map;
    }

    private Cell getNextCell(Cell[][] map, Cell currentCell) {
        var steps = new int[][]{
                {2, 0},
                {-2, 0},
                {0, 2},
                {0, -2}
        };

        var validNeighbouringCells = Arrays.stream(steps)
                .peek(step -> {
                    step[0] += currentCell.getXIndex();
                    step[1] += currentCell.getYIndex();
                }).filter(step -> {
                    if (step[0] <= 0 || step[0] >= this.height) {
                        return false;
                    } else if (step[1] <= 0 || step[1] >= this.width) {
                        return false;
                    }
                    return true;
                })
                .map(step -> map[step[0]][step[1]])
                .filter(step -> !visitHistory.contains(step))
                .collect(Collectors.toList());

        if (validNeighbouringCells.size() == 0) {
            return null;
        }

        var rndIndex = Random.getRandomNumber(0, validNeighbouringCells.size());
        return validNeighbouringCells.get(rndIndex);
    }
}