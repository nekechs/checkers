package com.nekechs.shpee.checkers.core;

public class NormalMove implements Move{
    PositionVector startingSpot;
    MoveVector.Direction moveDirection
    public NormalMove(PositionVector startingSpot, MoveVector.Direction moveDirection) {
        this.startingSpot = startingSpot;
        this.moveDirection = moveDirection;
    }
}
