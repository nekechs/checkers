package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.PositionVector;

public abstract class Move {
    abstract PositionVector getStartingSpot();
    abstract boolean isPlausible();
}
