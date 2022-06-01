package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.PositionVector;

import java.util.Optional;

/**
 * Specifies the state of a piece in a given instance. This is useful to have, because over time, a
 * piece's state may change (for example, the piece's position may change or the piece may get
 * captured). For this reason, it is important to have a class that stores a piece without the piece
 * itself storing information about what state it is in.
 * @author nekechs
 */
public class PieceState {
    /**
     * The actual piece itself.
     */
    private Piece pieceDelegation;

    /**
     * The position of the piece. If the optional is empty, then the piece is said to be captured.
     */
    private Optional<PositionVector> piecePosition;

    /**
     * Initializes a new PieceState with the actual piece itself and the position of the piece.
     * @param piece The piece that we would like to store information about
     * @param piecePosition The position of the piece that we want to keep
     */
    public PieceState(Piece piece, PositionVector piecePosition) {
        this.piecePosition = Optional.of(piecePosition);
        this.pieceDelegation = piece;
    }

    /**
     * Changes the piece that is stored inside of there
     * @param piece the new piece that is to be added
     */
    void changePiece(Piece piece) {
        this.pieceDelegation = piece;
    }

    void setPiecePosition(PositionVector position) {
        this.piecePosition = Optional.of(position);
    }

    void makePieceCaptured() {
        this.piecePosition = Optional.empty();
    }

    /**
     * Getter method for the piece that is in the given pieceState
     * @return gets the piece from the state
     */
    public Piece getPiece() {
        return pieceDelegation;
    }

    /**
     * Provides the position of the piece on the board, if it is not already captured.
     * @return position of piece on the board; (-1, -1) if the piece is captured
     */
    public PositionVector getPosition() {
        return piecePosition.orElse(new PositionVector(-1, -1));
    }

    /**
     * Checks if the piece has been captured or not.
     * @return true if piece is captured, false if piece is not already captured (if and only if the
     * piece still exists on the board)
     */
    public boolean isCaptured() {
        return !piecePosition.isPresent();
    }

    public String toString() {
        return pieceDelegation + " at " +
                piecePosition.orElseGet(() -> new PositionVector(-1, -1)) +
                "\n";
    }
}
