package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.BoardVector;
import com.nekechs.shpee.checkers.core.vectors.Movement;
import com.nekechs.shpee.checkers.core.vectors.PositionVector;
import com.nekechs.shpee.checkers.core.vectors.RelativeVector;
import com.nekechs.shpee.checkers.core.vectors.RelativeVectorFactory;
import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

import java.util.Optional;

public class NormalMove extends Move{
    PositionVector startingSpot;
    VectorFactory.Direction moveDirection;

    public NormalMove(PositionVector startingSpot, VectorFactory.Direction moveDirection) {
        this.startingSpot = startingSpot;
        this.moveDirection = moveDirection;
    }

    public PositionVector getStartingSpot() {
        return startingSpot;
    }

    public boolean isPlausible() {
        RelativeVectorFactory factory = new RelativeVectorFactory();
        Optional<BoardVector> possibleVector = factory.generateNormalMoveVector(moveDirection);

        if(possibleVector.isPresent()) {
            RelativeVector vector = new RelativeVector(possibleVector.get());
            PositionVector destination = startingSpot.addVector(vector);

            return destination.checkInBounds();
        }

        return false;
    }
}
