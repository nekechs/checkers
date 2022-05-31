package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.BoardVector;
import com.nekechs.shpee.checkers.core.vectors.Movement;
import com.nekechs.shpee.checkers.core.vectors.PositionVector;
import com.nekechs.shpee.checkers.core.vectors.RelativeVector;
import com.nekechs.shpee.checkers.core.vectors.RelativeVectorFactory;
import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

import java.util.Iterator;
import java.util.Optional;

public class NormalMove extends Move{
    PositionVector startingSpot;
    VectorFactory.Direction moveDirection;

    public NormalMove(PositionVector startingSpot, VectorFactory.Direction moveDirection) {
        this.startingSpot = startingSpot;
        this.moveDirection = moveDirection;
    }

    @Override
    public Iterator<PositionVector> iterator() {
        Iterator<PositionVector> it = new Iterator<PositionVector>() {
            PositionVector currentVector = startingSpot;
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < 2;
            }

            @Override
            public PositionVector next() {
                PositionVector prevVector = currentVector;
                currentVector = currentVector.addDirection(moveDirection, Movement.MOVEMENT_DISTANCE.DOUBLE);
                i++;
                return prevVector;
            }
        };

        return it;
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

    public String toString() {

        return "Start at: " + startingSpot.toString() + "; Direction: " + moveDirection.toString();
    }

    @Override
    public boolean isPromotionAttempt(Team team) {
        PositionVector finalSpot = startingSpot.addDirection(moveDirection, Movement.MOVEMENT_DISTANCE.SINGLE);
        return checkPromotionSquare(team, finalSpot);
    }
}
