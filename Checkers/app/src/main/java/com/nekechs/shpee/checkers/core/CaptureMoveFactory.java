package com.nekechs.shpee.checkers.core;

import java.util.ArrayList;
import java.util.List;

public class CaptureMoveFactory extends MoveFactory{
    public Move generateMove(List<PositionVector> positionVectorList) {
        if(positionVectorList.size() < 2) {
            return new CaptureMove(new PositionVector(0,0), new ArrayList<>());
        }

        List<PositionVector> successiveMoveList = new ArrayList<>(positionVectorList);
        successiveMoveList.remove(0);
        return new CaptureMove(positionVectorList.get(0), successiveMoveList);
    }

    public Move generateMove(PositionVector startingPosition, List<MoveVector> moveVectorList) {
        List<PositionVector> successiveMoveList = new ArrayList<>();
        PositionVector lastestMoveSpot = new PositionVector(startingPosition);

        for(MoveVector vect : moveVectorList) {
            lastestMoveSpot = lastestMoveSpot.addVector(vect);
            successiveMoveList.add(new PositionVector(lastestMoveSpot));
        }

        return new CaptureMove(startingPosition, successiveMoveList);
    }
}
