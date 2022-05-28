package com.nekechs.shpee.checkers.core.vectors;

public class RelativeVector extends BoardVector{
    public RelativeVector(int row, int col) {
        super(row, col);
    }
    public RelativeVector(BoardVector b) {
        super(b.row, b.col);
    }
}
