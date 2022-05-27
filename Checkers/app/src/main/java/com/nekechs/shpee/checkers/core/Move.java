package com.nekechs.shpee.checkers.core;

public interface Move {
    PositionVector getStartingSpot();
    boolean isPlausible();
}
