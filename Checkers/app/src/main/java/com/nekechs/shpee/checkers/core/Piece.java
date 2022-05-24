package com.nekechs.shpee.checkers.core;

import android.graphics.Point;

import java.util.List;

public abstract class Piece {
    final Team team;
    private PositionVector position;
    final Board parentBoard;

    public Piece(PositionVector startingPosition, Team team) {
        this.position = startingPosition;
        this.team = team;
        parentBoard = team.board;

        team.addPiece(this);
    }

    public Piece() {
        team = null;
        parentBoard = null;
    }

    Piece(Piece p) {
        this.team = p.team;
        this.position = new PositionVector(p.position);
        parentBoard =
    }

    //TODO: Figure out move logic based on how checkers works
    public abstract void move(Move move);
    public abstract char getPieceType();

    public PositionVector getPosition() {
        return position;
    }

    public int getRow() {
        return position.row;
    }

    public int getCol() {
        return position.col;
    }

    public Team getPieceTeam() {
        return team;
    }

    public String toString() {
        return Character.toString(team.getTeamColor()) + Character.toString(getPieceType());
    }
}
