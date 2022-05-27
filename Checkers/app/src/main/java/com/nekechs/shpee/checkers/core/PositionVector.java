package com.nekechs.shpee.checkers.core;

import java.util.Optional;

public class PositionVector extends BoardVector {
    // The number of rows and columns that are present
    private int maxRow = 8;
    private int maxCol = 8;

    public PositionVector() {
        super(7,7);
    }

    public PositionVector(int row, int col) {
        super(0,0);
        super.row = row % getMaxRow();
        super.col = col % getMaxCol();
    }

    PositionVector(BoardVector v) {
        super(v);
    }

    public PositionVector addVector(BoardVector vector) {
        int newRow = super.row + vector.row;
        int newCol = super.col + vector.col;

        PositionVector newVector = new PositionVector(newRow, newCol);

        return newVector;
    }

    public PositionVector subtractVector(BoardVector vector) {
        int newRow = super.row - vector.row;
        int newCol = super.col - vector.col;

        PositionVector newVector = new PositionVector(newRow, newCol);

        return newVector;
    }

    public Optional<MoveVector> getDiagonalVectorFromSubtract(BoardVector vector) {
        int newRow = super.row - vector.row;
        int newCol = super.col - vector.col;

        if(Math.abs(newRow) != Math.abs(newCol)) {
            return Optional.empty();
        }

        MoveVector relativeVector;

        relativeVector = MoveVector.getClosestDiagnonalVector(newRow, newCol);

        if(relativeVector.magnitude == 0) {
            return Optional.empty();
        }
        return Optional.of(relativeVector);
    }

    /**
     * Gets the number of rows.
     * @return Number guaranteed to be at least 1.
     */
    public int getMaxRow() {
        return maxRow;
    }

    /**
     * Gets the number of columns.
     * @return Number guaranteed to be at least 1.
     */
    public int getMaxCol() {
        return maxCol;
    }

    /**
     * Checks whether or not the vector is within the bounds of the checkers board, as defined by
     * maxRow and maxCol.
     *
     * @return boolean that specifies whether or not the position vector is within the bounds of the
     * vector.
     */
    public boolean checkInBounds() {
        return super.row < getMaxRow() &&
                super.row >= 0 &&
                super.col < getMaxCol() &&
                super.col >= 0;
    }
}
