package com.nekechs.shpee.checkers.core.vectors;

import com.nekechs.shpee.checkers.core.Board;

import java.util.Optional;

public abstract class VectorFactory {
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
        public boolean sharesDirection(VectorFactory.Direction direction) {
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

    public abstract Optional<BoardVector> generateVector(int row, int col);
    public abstract Optional<BoardVector> generateVector(Direction direction, int magnitude);
    public Optional<BoardVector> generateCaptureVector(Direction direction) {
        return generateVector(direction, 2);
    }
}
