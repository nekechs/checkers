package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

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
    public List<VectorFactory.Direction> getPathDirections() {
        return new ArrayList<>();
    }
}
