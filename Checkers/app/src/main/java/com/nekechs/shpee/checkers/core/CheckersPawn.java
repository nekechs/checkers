package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.PositionVector;
import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

import java.util.ArrayList;
import java.util.List;

public class CheckersPawn extends Piece{

    public CheckersPawn(Team team) {
        super(team);
    }

    @Override
    public void move(Move proposedMove) {

    }

    @Override
    public boolean isValidMoveDirection(VectorFactory.Direction direction) {
        assert super.team != null;
        VectorFactory.Direction properMoveDirection = super.team.movementDirection;
        switch (properMoveDirection) {
            case NORTH:
                return direction.equals(VectorFactory.Direction.NORTHEAST) ||
                        direction.equals(VectorFactory.Direction.NORTHWEST);
            case SOUTH:
                return direction.equals(VectorFactory.Direction.SOUTHEAST) ||
                        direction.equals(VectorFactory.Direction.SOUTHWEST);
            default:
                return false;
        }
    }

    @Override
    public List<VectorFactory.Direction> getPathDirections() {
//        if(super.team.movementDirection == VectorFactory.Direction.NORTH) {
//            return List.of(VectorFactory.Direction.NORTHEAST, VectorFactory.Direction.NORTHWEST);
//        }
        assert super.team != null;
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
