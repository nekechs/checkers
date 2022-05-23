package com.nekechs.shpee.checkers.core;

public class MoveVector extends BoardVector{
    public static enum Direction {
        NORTH,
        NORTHEAST,
        EAST,
        SOUTHEAST,
        SOUTH,
        SOUTHWEST,
        WEST,
        NORTHWEST
    }

    /**
     * We need this because checkers moves only exist within 8 cardinal directions, and it's easier
     * to classify them as an enumeration.
     */
    Direction direction;

    /**
     * This is the only constructor present in MoveVector. Reason for this is that relative vectors
     * do not exist within a vacuum, but rather exist as 8 cardinal directions in checkers.
     * <p>
     * Note that when we specify the "direction" enum, we are referring to the direction as a direction
     * corresponding to the board where white is on the bottom and black is on top. Does <em>not</em>
     * take into consideration which team is relevant to the direction.
     *
     * @param direction the direction that we want the MoveVector to point in.
     */
    public MoveVector(Direction direction) {
        super(0,0);
        switch(direction) {
            case NORTH:
                super.row = 0;
                super.col = 1;
                break;
            case NORTHEAST:
                super.row = 1;
                super.col = 1;
                break;
            case EAST:
                super.row = 1;
                super.col = 0;
                break;
            case SOUTHEAST:
                super.row = 1;
                super.col = -1;
                break;
            case SOUTH:
                super.row = 0;
                super.col = -1;
                break;
            case SOUTHWEST:
                super.row = -1;
                super.col = -1;
                break;
            case WEST:
                super.row = -1;
                super.col = 0;
                break;
            case NORTHWEST:
                super.row = -1;
                super.col = 1;
                break;
        }
    }

}
