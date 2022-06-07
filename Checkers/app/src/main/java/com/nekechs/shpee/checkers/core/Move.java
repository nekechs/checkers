package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.Movement;
import com.nekechs.shpee.checkers.core.vectors.PositionVector;
import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

import java.util.Iterator;

public abstract class Move implements Iterable<PositionVector>{
    abstract PositionVector getStartingPosition();
    abstract boolean isPlausible();
    public abstract boolean isPromotionAttempt(Team team);
    public abstract PositionVector getFinalSpot();

    /**
     * Gets the standard notation of a given move in checkers. Very closely resembles the chess form
     * of notation, with some modifications. The only difference is in the fact that when pieces go
     * to capture, all of the positions that the jumping piece visits are listed out.
     * <p>
     * Examples:
     * e3 g5 e7
     * d6 e5
     * @return A string containing the move, that if parsed by MoveParser, it produces the exact same
     * move.
     */
    public String getPositionNotation() {
        Iterator<PositionVector> it = this.iterator();

        StringBuilder stringBuilder = new StringBuilder();

        while(it.hasNext()) {
            stringBuilder.append(it.next().getStandardPositionNotation());
            if(it.hasNext()) stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    public boolean checkPromotionSquare(Team team, PositionVector finalPosition) {
        return (team.movementDirection == VectorFactory.Direction.NORTH && finalPosition.getRow() == 0) ||
                (team.movementDirection == VectorFactory.Direction.SOUTH && finalPosition.getRow() == 7);
    }
}
