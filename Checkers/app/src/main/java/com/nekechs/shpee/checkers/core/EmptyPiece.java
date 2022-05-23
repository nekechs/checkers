package com.nekechs.shpee.checkers.core;

public class EmptyPiece extends Piece{

    @Override
    public void move(Move move) {

    }
    public char getPieceType() {
        return ' ';
    }

    public String toString() {
        return "  ";
    }
}
