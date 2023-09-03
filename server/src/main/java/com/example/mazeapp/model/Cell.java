package com.example.mazeapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cell extends Point {
    private boolean canBeVisited;

    public Cell(boolean canBeVisited, int xIndex, int yIndex) {
        super(xIndex, yIndex);
        this.canBeVisited = canBeVisited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (xIndex != cell.xIndex) return false;
        return yIndex == cell.yIndex;
    }

    @Override
    public int hashCode() {
        int result = xIndex;
        result = 31 * result + yIndex;
        return result;
    }
}