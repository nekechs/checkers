package com.nekechs.shpee.checkers.core;

public class MoveVector extends BoardVector{
    public static enum Direction {
        NORTH(0),
        NORTHEAST(1),
        EAST(2),
        SOUTHEAST(3),
        SOUTH(4),
        SOUTHWEST(5),
        WEST(6),
        NORTHWEST(7);

        final private int id;

        Direction(int id) {
            this.id = id;
        }

        /**
         * Basically gets the ID of the enum. Each enum is associated with a numerical ID.
         * @return some integer representing ID of an enumeration.
         */
        public int getID() {
            return id;
        }

        /**
         * Checks if a given direction shares the direction with another direction (i.e. maximum
         * "angle" between the two directions is 90 degrees).
         * @param direction A direction that this enum will be compared against
         * @return true if the directions are shared, false if not.
         */
        public boolean sharesDirection(Direction direction) {
            int directionID = direction.getID();
            for(int i = -2; i <= 2; i++) {
                int cmpID = (directionID + i) % 8;
                if(cmpID == this.id) {
                    return true;
                }
            }
            return false;
        }
    }

    public final int[] grayCodeList = {0, 1, 3, 2, 6, 7, 5, 4};

    /**
     * We need this because checkers moves only exist within 8 cardinal directions, and it's easier
     * to classify them as an enumeration.
     */
    Direction direction;

    /**
     * This is to specify how big the move is in a particular direction.
     */
    int magnitude;

    /**
     * This makes a MoveVector in a specified cardinal direction.
     * <p>
     * Note that when we specify the "direction" enum, we are referring to the direction as a direction
     * corresponding to the board where white is on the bottom and black is on top. Does <em>not</em>
     * take into consideration which team is relevant to the direction.
     *
     * @param direction the direction that we want the MoveVector to point in.
     */
    public MoveVector(Direction direction, int magnitude) {
        super(0,0);
        this.magnitude = magnitude;

        switch(direction) {
            case NORTH:
                super.row = 0;
                super.col = magnitude;
                break;
            case NORTHEAST:
                super.row = magnitude;
                super.col = magnitude;
                break;
            case EAST:
                super.row = magnitude;
                super.col = 0;
                break;
            case SOUTHEAST:
                super.row = magnitude;
                super.col = -magnitude;
                break;
            case SOUTH:
                super.row = 0;
                super.col = -magnitude;
                break;
            case SOUTHWEST:
                super.row = -magnitude;
                super.col = -magnitude;
                break;
            case WEST:
                super.row = -magnitude;
                super.col = 0;
                break;
            case NORTHWEST:
                super.row = -magnitude;
                super.col = magnitude;
                break;
            default:
                super.row = 0;
                super.col = 0;
                break;
        }
    }

    static MoveVector getClosestDiagnonalVector(int row, int col) {
        if(row == 0 && col == 0)
            return new MoveVector(Direction.NORTHEAST, 0);

        // We want to get the dominant component so we use this as the magnitude
        int greatestMagnitude = Math.max(Math.abs(row), Math.abs(col));

        int rowDirection = row / (Math.max(1, Math.abs(row)));
        int colDirection = col / (Math.max(1, Math.abs(col)));

        if(rowDirection == 1 && colDirection == 1) {
            return new MoveVector(Direction.NORTHEAST, greatestMagnitude);
        }
        if(rowDirection == -1 && colDirection == 1) {
            return new MoveVector(Direction.SOUTHEAST, greatestMagnitude);
        }
        if(rowDirection == -1 && colDirection == -1) {
            return new MoveVector(Direction.SOUTHWEST, greatestMagnitude);
        }
        if(rowDirection == 1 && colDirection == -1) {
            return new MoveVector(Direction.NORTHWEST, greatestMagnitude);
        }
        return new MoveVector(Direction.NORTHEAST, 0);
    }
}
