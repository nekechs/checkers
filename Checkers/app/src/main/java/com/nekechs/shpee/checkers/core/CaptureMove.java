package com.nekechs.shpee.checkers.core;

import java.util.List;

public class CaptureMove implements Move {
    List<MoveVector.Direction> movementSequence;
    PositionVector startingPiece;

    public CaptureMove(PositionVector startingPiece, List<MoveVector.Direction> movementSequence) {
        this.startingPiece = startingPiece;
        this.movementSequence = movementSequence;
    }

    /**
     * A method that basically checks whether or not this move is valid on a checkers board. This
     * entails the following: Check whether or not the movements are consecutive, check whether the
     * movements are diagonal, check whether the movements move 2 squares at most, and check whether
     * the movements are in bounds.
     * <p>
     * Note: this does <em>not check</em> for whether or not the moves are valid for the given piece,
     * nor does it actually check for any other sort of validity.
     *
     * @return boolean that represents if the move is plausible on the checkers board.
     */
    public boolean isPlausible() {
        MoveVector currentMovement;
        PositionVector lastPositionSpot;

        for(MoveVector.Direction directions : movementSequence) {

        }
        return true;
    }

    public PositionVector getStartingSpot() {
        return startingPiece;
    }
}
