package com.nekechs.shpee.checkers.core;

import java.util.List;
import java.util.LinkedList;

public class Move {
    // This is the sequence of moves that is propsosed
    List<MoveVector> moveSequence;
    PositionVector startingPiece;

    public Move(PositionVector startingPiece, List<MoveVector> moveSequence) {
        this.startingPiece = startingPiece;
        this.moveSequence = moveSequence;
    }
}
