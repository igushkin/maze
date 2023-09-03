package com.example.mazeapp.util;

import com.example.mazeapp.model.Cell;
import com.example.mazeapp.model.Maze;
import com.example.mazeapp.model.Point;
import lombok.RequiredArgsConstructor;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

public class MazeSolver {
    private final Maze maze;

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public List<Point> solve() {
        return solve(this.maze, maze.getStartCell(), null, maze.getExitCell());
    }

    private List<Point> solve(Maze maze, Cell currentCell, Cell prevCell, Cell exitCell) {
        if (currentCell.equals(exitCell)) {
            return new ArrayList<>(List.of(currentCell));
        } else {
            List<Cell> neighboringCells = getNeighboringCells(maze, currentCell, prevCell);
            List<Point> visitHistory = new ArrayList<>(List.of(currentCell));

            while (neighboringCells.size() > 0) {
                var rndIndex = Random.getRandomNumber(0, neighboringCells.size());
                var nextCell = neighboringCells.get(rndIndex);
                neighboringCells.remove(rndIndex);

                var searchPath = this.solve(maze, nextCell, currentCell, exitCell);
                visitHistory.addAll(searchPath);

                if (visitHistory.get(visitHistory.size() - 1).equals(exitCell)) {
                    return visitHistory;
                }
            }
            visitHistory.add(prevCell);
            return visitHistory;
        }
    }

    private List<Cell> getNeighboringCells(Maze maze, Cell currentCell, Cell prevCell) {
        var steps = new int[][]{
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };

        return Arrays.stream(steps)
                .peek(step -> {
                    step[0] += currentCell.getXIndex();
                    step[1] += currentCell.getYIndex();
                })
                .map(step -> maze.getCell(step[0], step[1]))
                .filter(cell -> cell.isCanBeVisited())
                .filter(cell -> !cell.equals(prevCell))
                .collect(Collectors.toList());
    }
}