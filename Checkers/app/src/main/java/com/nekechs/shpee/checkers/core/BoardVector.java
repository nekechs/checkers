package com.nekechs.shpee.checkers.core;

public abstract class BoardVector {
    int row;
    int col;

    public BoardVector(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
//    public abstract int getMaxRow();
//    public abstract int getMaxCol();

}
