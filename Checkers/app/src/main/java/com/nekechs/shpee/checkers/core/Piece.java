package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.PositionVector;
import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

import java.util.List;

public abstract class Piece {
//    private PositionVector position;
    public final Team team;

    public Piece(Team team) {
        this.team = team;

        team.pieceList.add(this);
    }

    public Piece() {
        this.team = null;
    }

    //TODO: Figure out move logic based on how checkers works
    public abstract void move(Move move);

    public abstract List<VectorFactory.Direction> getPathDirections();
    public abstract char getPieceType();
    public abstract boolean isValidMoveDirection(VectorFactory.Direction direction);

//    public PositionVector getPosition() {
//        return position;
//    }
//    void setPosition(PositionVector position) {
//        this.position = position;
//    }

//    public int getRow() {
//        return position.getRow();
//    }
//
//    public int getCol() {
//        return position.getCol();
//    }

    public String toString() {
        return Character.toString(team.getTeamColor()) + Character.toString(getPieceType());
    }
}
