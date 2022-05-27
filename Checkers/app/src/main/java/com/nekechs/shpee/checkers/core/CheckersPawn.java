package com.nekechs.shpee.checkers.core;

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
    public List<MoveVector.Direction> getPathDirections() {
        if(super.team.movementDirection == MoveVector.Direction.NORTH) {
            return List.of(MoveVector.Direction.NORTHEAST, MoveVector.Direction.NORTHWEST);
        }

        switch(super.team.movementDirection) {
            case NORTH:
                return List.of(MoveVector.Direction.NORTHEAST, MoveVector.Direction.NORTHWEST);
            case SOUTH:
                return List.of(MoveVector.Direction.SOUTHEAST, MoveVector.Direction.SOUTHWEST);
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public char getPieceType() {
        return 'p';
    }
}
