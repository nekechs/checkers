package com.nekechs.shpee.checkers.core;

public class CheckersPawn extends Piece{
    public CheckersPawn(PositionVector vect, Team team) {
        super(vect, team);
    }

    @Override
    public void move(Move move) {

    }

    @Override
    public char getPieceType() {
        return 'p';
    }
}
