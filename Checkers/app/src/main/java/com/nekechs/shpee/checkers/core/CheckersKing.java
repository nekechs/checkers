package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.PositionVector;
import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

import java.util.List;

public class CheckersKing extends Piece{
    public CheckersKing(PositionVector vect, Team team) {
        super(vect, team);
    }

    @Override
    public void move(Move move) {

    }

    @Override
    public List<VectorFactory.Direction> getPathDirections() {
        return List.of(VectorFactory.Direction.NORTHWEST,
                        VectorFactory.Direction.NORTHEAST,
                        VectorFactory.Direction.SOUTHEAST,
                        VectorFactory.Direction.SOUTHWEST);
    }

    @Override
    public boolean isValidMoveDirection(VectorFactory.Direction direction) {
        return direction == VectorFactory.Direction.NORTHWEST ||
                direction == VectorFactory.Direction.NORTHEAST ||
                direction == VectorFactory.Direction.SOUTHEAST ||
                direction == VectorFactory.Direction.SOUTHWEST;
    }

    @Override
    public char getPieceType() {
        return 'k';
    }
}
