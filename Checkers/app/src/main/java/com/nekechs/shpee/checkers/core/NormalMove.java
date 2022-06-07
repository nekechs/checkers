package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.BoardVector;
import com.nekechs.shpee.checkers.core.vectors.Movement;
import com.nekechs.shpee.checkers.core.vectors.PositionVector;
import com.nekechs.shpee.checkers.core.vectors.RelativeVector;
import com.nekechs.shpee.checkers.core.vectors.RelativeVectorFactory;
import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

public class NormalMove extends Move{
    PositionVector startingPosition;
    VectorFactory.Direction moveDirection;

    public NormalMove(PositionVector startingSpot, VectorFactory.Direction moveDirection) {
        this.startingPosition = startingSpot;
        this.moveDirection = moveDirection;
    }

    @Override
    public Iterator<PositionVector> iterator() {
        Iterator<PositionVector> it = new Iterator<PositionVector>() {
            PositionVector currentVector = startingPosition;
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < 2;
            }

            @Override
            public PositionVector next() {
                PositionVector prevVector = currentVector;
                currentVector = currentVector.addDirection(moveDirection, Movement.MOVEMENT_DISTANCE.SINGLE);
                i++;
                return prevVector;
            }
        };

        return it;
    }

    public PositionVector getStartingPosition() {
        return startingPosition;
    }

    @Override
    public PositionVector getFinalSpot() {
        return startingPosition.addDirection(moveDirection, Movement.MOVEMENT_DISTANCE.SINGLE);
    }

    public boolean isPlausible() {
        RelativeVectorFactory factory = new RelativeVectorFactory();
        Optional<BoardVector> possibleVector = factory.generateNormalMoveVector(moveDirection);

        if(possibleVector.isPresent()) {
            RelativeVector vector = new RelativeVector(possibleVector.get());
            PositionVector destination = startingPosition.addVector(vector);

            return destination.checkInBounds();
        }

        return false;
    }

    public String toString() {

        return "Start at: " + startingPosition.toString() + "; Direction: " + moveDirection.toString();
    }

    @Override
    public boolean isPromotionAttempt(Team team) {
        PositionVector finalSpot = startingPosition.addDirection(moveDirection, Movement.MOVEMENT_DISTANCE.SINGLE);
        return checkPromotionSquare(team, finalSpot);
    }
}
