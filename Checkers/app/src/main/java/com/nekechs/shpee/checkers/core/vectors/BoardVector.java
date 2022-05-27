package com.nekechs.shpee.checkers.core.vectors;

public abstract class BoardVector {
    final int row;
    final int col;

    public BoardVector(int row, int col) {
        this.row = row;
        this.col = col;
    }

    BoardVector(BoardVector v) {
        this.row = v.row;
        this.col = v.col;
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
