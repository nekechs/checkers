package com.nekechs.shpee.checkers.core.vectors;

import com.nekechs.shpee.checkers.core.Move;

import java.util.Optional;

public class PositionVector extends BoardVector {
    // The number of rows and columns that are present
    private static final int maxRow = 8;
    private static final int maxCol = 8;

    public PositionVector() {
        super(7,7);
    }

    public PositionVector(int row, int col) {
        super(row,col);
    }

    PositionVector(BoardVector v) {
        super(v);
    }

    public PositionVector addVector(RelativeVector vector) {
        int newRow = super.row + vector.row;
        int newCol = super.col + vector.col;

        return new PositionVector(newRow, newCol);
    }

    public PositionVector addMovement(Movement movement) {
        return addVector(movement.vector);
    }

    public PositionVector addDirection(VectorFactory.Direction direction, Movement.MOVEMENT_DISTANCE distance) {
        return addMovement(new Movement(direction, distance));
    }

    public RelativeVector minusVector(BoardVector vector) {
        int newRow = super.row - vector.row;
        int newCol = super.col - vector.col;

        RelativeVector newVector = new RelativeVector(newRow, newCol);

        return newVector;
    }

    public Optional<Movement> getDiagonalVectorFromSubtract(BoardVector vector) {
        int newRow = super.row - vector.row;
        int newCol = super.col - vector.col;

        if(Math.abs(newRow) != Math.abs(newCol)) {
            return Optional.empty();
        }

        Optional<Movement> relativeVector;

        relativeVector = Movement.getClosestDiagnonalVector(newRow, newCol);

        return relativeVector;
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
