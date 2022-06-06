package com.nekechs.shpee.checkers.core.vectors;

import com.nekechs.shpee.checkers.core.Board;

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

    @Override
    public int hashCode() {
        return (this.row + this.col - 1) / 2;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof BoardVector) {
            BoardVector vector = (BoardVector) o;

            return vector.col == this.col && vector.row == this.row;
        }

        return false;
    }

    public String toString() {
        return "VECTOR: (" + row + ", " + col + ")";
    }

}
