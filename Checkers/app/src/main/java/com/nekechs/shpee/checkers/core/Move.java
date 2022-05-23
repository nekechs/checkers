package com.nekechs.shpee.checkers.core;

import java.util.List;
import java.util.LinkedList;

public class Move {
    // This is the sequence of moves that is propsosed
    List<MoveVector> moveSequence;

    public Move() {
        moveSequence = new LinkedList<>();
    }

    public Move(List<MoveVector> moveSequence) {
        this.moveSequence = moveSequence;
    }
}
