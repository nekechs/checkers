package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.Movement;
import com.nekechs.shpee.checkers.core.vectors.PositionVector;
import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

import java.util.ArrayList;
import java.util.List;

public class CheckersPawn extends Piece{

    public CheckersPawn(PositionVector vect, Team team) {
        super(vect, team);
    }

    @Override
    public void move(CaptureMove proposedMove) {

    }

    @Override
    public List<VectorFactory.Direction> getPathDirections() {
        if(super.team.movementDirection == VectorFactory.Direction.NORTH) {
            return List.of(VectorFactory.Direction.NORTHEAST, VectorFactory.Direction.NORTHWEST);
        }

        switch(super.team.movementDirection) {
            case NORTH:
                return List.of(VectorFactory.Direction.NORTHEAST, VectorFactory.Direction.NORTHWEST);
            case SOUTH:
                return List.of(VectorFactory.Direction.SOUTHEAST, VectorFactory.Direction.SOUTHWEST);
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public char getPieceType() {
        return 'p';
    }
}
