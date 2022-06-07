package com.nekechs.shpee.checkers.core.vectors;

import com.nekechs.shpee.checkers.core.Move;

import java.util.Optional;

public class PositionVector extends BoardVector {
    // The number of rows and columns that are present
    private static final int maxRow = 8;
    private static final int maxCol = 8;

    /**
     * no-arg constructor that just initializes the PositionVector to the top left corner of the board:
     * row: 7, col: 7
     */
    public PositionVector() {
        super(7,7);
    }

    /**
     * The most useful constructor that takes in a row and column and stores it inside of this
     * PositionVector. This is used because it is helpful to encapsulate the row and column positions
     * inside of a single constructor.
     * @param row Location of a position's row on the board (think a<em>5</em>)
     * @param col Location of a position's column on the board (think <em>a</em>5)
     */
    public PositionVector(int row, int col) {
        super(row,col);
    }

    /**
     * Takes a board vector and makes a position vector out of it, whether it is a relative vector
     * or a position vector. It does not matter, as all it's really doing is taking row and column
     * and copying those values to this vector.
     * @param v The vector that you want to copy over.
     */
    public PositionVector(BoardVector v) {
        super(v);
    }

    /**
     * An analog to vector addition. This takes a relative vector and adds it to this position vector.
     * It could come in handy for reuse of code in a chess application, perchance.
     * @param vector The vector that you want to add to this vector
     * @return The vector that results from vector addition.
     */
    public PositionVector addVector(RelativeVector vector) {
        int newRow = super.row + vector.row;
        int newCol = super.col + vector.col;

        return new PositionVector(newRow, newCol);
    }

    /**
     * Instead of adding just a plain old vector, this method adds a movement, which does in fact
     * store a vector. It is just that this vector is confined to be a diagonal vector that jumps
     * either 1 space or 2 spaces diagonally. The most use for this comes out of the fact that in
     * Checkers, 'moves' solely consist of diagonal moves, either 1 or 2 spaces long.
     * @param movement A movement: basically a diagonal RelativeVector of length 1 or 2.
     * @return A PositionVector that results from taking a movement from that initial position.
     */
    public PositionVector addMovement(Movement movement) {
        return addVector(movement.vector);
    }

    /**
     * Adds on a direction to the given Position using a direction and a Distance. Basically the same
     * thing as adding on a Movement.
     * @param direction The direction that the new PositionVector should be in
     * @param distance How far away the new PositionVector will be
     * @return A PositionVector that is basically the position that is a distance away from the original
     * in a certain direction
     */
    public PositionVector addDirection(VectorFactory.Direction direction, Movement.MOVEMENT_DISTANCE distance) {
        return addMovement(new Movement(direction, distance));
    }

    public RelativeVector minusVector(BoardVector vector) {
        int newRow = super.row - vector.row;
        int newCol = super.col - vector.col;

        return new RelativeVector(newRow, newCol);
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

    public String getStandardPositionNotation() {
        char fileChar = (char) (col + 97);

        return Character.toString(fileChar) + (8 - row);
    }
}
