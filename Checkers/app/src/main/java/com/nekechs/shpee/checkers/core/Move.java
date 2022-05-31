package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.PositionVector;
import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

public abstract class Move implements Iterable<PositionVector>{
    abstract PositionVector getStartingSpot();
    abstract boolean isPlausible();
    public abstract boolean isPromotionAttempt(Team team);

    public boolean checkPromotionSquare(Team team, PositionVector finalPosition) {
        return (team.movementDirection == VectorFactory.Direction.NORTH && finalPosition.getRow() == 0) ||
                (team.movementDirection == VectorFactory.Direction.SOUTH && finalPosition.getRow() == 7);
    }
}
