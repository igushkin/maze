package com.example.mazeapp.controller;

import com.example.mazeapp.model.Maze;
import com.example.mazeapp.model.Point;
import com.example.mazeapp.util.MapGenerator;
import com.example.mazeapp.util.MazeSolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AppController {
    @GetMapping
    public ResponseEntity<Maze> generateMaze(@RequestParam(required = false, defaultValue = "21") int height,
                                             @RequestParam(required = false, defaultValue = "21") int width) {
        var mapGenerator = new MapGenerator(height, width);
        return new ResponseEntity<>(
                mapGenerator.generateMap(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<List<Point>> getWayOut(@RequestBody Maze maze) {
        var mapSolver = new MazeSolver(maze);
        return new ResponseEntity<>(mapSolver.solve(), HttpStatus.OK);
    }
}