package com.nekechs.shpee.checkers.core.vectors;

import java.util.Optional;

public class Movement{
    public enum MOVEMENT_DISTANCE {
        SINGLE(1),
        DOUBLE(2);

        final private int distance;

        MOVEMENT_DISTANCE(int distance) {
            this.distance = distance;
        }

        public int getDistance() {
            return distance;
        }
    }

    public final int[] grayCodeList = {0, 1, 3, 2, 6, 7, 5, 4};
    private RelativeVector movementVector(VectorFactory.Direction direction, MOVEMENT_DISTANCE distanceHopped) {
        return (RelativeVector)(new RelativeVectorFactory())
                .generateVector(direction, distanceHopped.getDistance())
                .orElse(new RelativeVector(0,0));
    }

    /**
     * We need this because checkers moves only exist within 8 cardinal directions, and it's easier
     * to classify them as an enumeration.
     */
    VectorFactory.Direction direction;

    /**
     * This is to specify how big the move is in a particular direction.
     */
    MOVEMENT_DISTANCE distance;

    /**
     * This is the vector itself, which is compatible with the PositionVector and MovementVector
     */
    RelativeVector vector;

    /**
     * This makes a MoveVector in a specified cardinal direction.
     * <p>
     * Note that when we specify the "direction" enum, we are referring to the direction as a direction
     * corresponding to the board where white is on the bottom and black is on top. Does <em>not</em>
     * take into consideration which team is relevant to the direction.
     *
     * @param direction the direction that we want the MoveVector to point in.
     */
    public Movement(VectorFactory.Direction direction, MOVEMENT_DISTANCE distance) {
        this.direction = direction;
        this.distance = distance;

        this.vector = movementVector(direction, distance);
    }

    static Optional<Movement> getClosestDiagnonalVector(int row, int col) {
        return getClosestDiagnonalVector(row, col, MOVEMENT_DISTANCE.SINGLE);
    }

    static Optional<Movement> getClosestDiagnonalVector(int row, int col, MOVEMENT_DISTANCE distance) {
        if(row == 0 && col == 0)
            return Optional.empty();

        // We want to get the dominant component so we use this as the magnitude
        int greatestMagnitude = Math.max(Math.abs(row), Math.abs(col));

        int rowDirection = row / (Math.max(1, Math.abs(row)));
        int colDirection = col / (Math.max(1, Math.abs(col)));

        if(rowDirection == 1 && colDirection == 1) {
            return Optional.of(new Movement(VectorFactory.Direction.SOUTHEAST, distance));
        }
        if(rowDirection == -1 && colDirection == 1) {
            return Optional.of(new Movement(VectorFactory.Direction.NORTHEAST, distance));
        }
        if(rowDirection == -1 && colDirection == -1) {
            return Optional.of(new Movement(VectorFactory.Direction.NORTHWEST, distance));
        }
        if(rowDirection == 1 && colDirection == -1) {
            return Optional.of(new Movement(VectorFactory.Direction.SOUTHWEST, distance));
        }

        return Optional.empty();
    }

    public static Optional<Movement> possibleVectorToMovement(BoardVector vector) {
        int rowVal = vector.row;
        int colVal = vector.col;

        int rowMagnitude = Math.abs(rowVal);
        int colMagnitude = Math.abs(colVal);

        if(rowMagnitude == colMagnitude) {
            if(rowMagnitude == 1) {
                return getClosestDiagnonalVector(rowVal, colVal);
            }
            if(colMagnitude == 2) {
                return getClosestDiagnonalVector(rowVal, colVal, MOVEMENT_DISTANCE.DOUBLE);
            }
        }
        return Optional.empty();
    }

    public VectorFactory.Direction getDirection() {
        return direction;
    }

    public MOVEMENT_DISTANCE getDistance() {
        return distance;
    }
}
