package com.nekechs.shpee.checkers.core;

public class CheckersVector extends BoardVector {
    private int maxRow;
    private int maxCol;

    public CheckersVector() {
        this(8,8);
    }

    public CheckersVector(int row, int col) {
        this.maxRow = row;
        this.maxCol = col;
    }
}
