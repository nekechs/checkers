package com.nekechs.shpee.checkers.core;

import java.util.ArrayList;
import java.util.List;

public class EmptyPiece extends Piece{

    @Override
    public void move(CaptureMove move) {

    }
    public char getPieceType() {
        return ' ';
    }

    public String toString() {
        return "  ";
    }

    @Override
    public List<MoveVector.Direction> getPathDirections() {
        return new ArrayList<>();
    }
}
