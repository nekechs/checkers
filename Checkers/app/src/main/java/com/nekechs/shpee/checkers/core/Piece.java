package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.PositionVector;
import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

import java.util.List;

public abstract class Piece {
    private PositionVector position;
    final Team team;

    public Piece(PositionVector startingPosition, Team team) {
        this.position = startingPosition;
        this.team = team;
    }

    public Piece() {
        this.team = null;
    }

//    Piece(Piece p) {
//        this.team = p.team;
//        this.position = new PositionVector(p.position);
//        parentBoard =
//    }

    //TODO: Figure out move logic based on how checkers works
    public abstract void move(CaptureMove move);

    public abstract List<VectorFactory.Direction> getPathDirections();
    public abstract char getPieceType();

    public PositionVector getPosition() {
        return position;
    }

    public int getRow() {
        return position.getRow();
    }

    public int getCol() {
        return position.getCol();
    }

    public String toString() {
        return Character.toString(team.getTeamColor()) + Character.toString(getPieceType());
    }
}
