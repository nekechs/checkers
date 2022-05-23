package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.exceptions.OutOfBoardException;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public abstract class Board {
    int maxRow;
    int maxCol;

    ArrayList<ArrayList<Optional<Piece>>> pieceGrid;

    public Board(int maxRow, int maxCol) {
        this.maxRow = maxRow;
        this.maxCol = maxCol;

        pieceGrid = new ArrayList<ArrayList<Optional<Piece> >>();
        // Note that we do not actually put the pieces on the board yet, because that is done through
        // the subclass of Board.

        for(int i = 0; i < maxRow; i++) {
            ArrayList<Optional<Piece>> rowArray = new ArrayList<Optional<Piece>>();
            for(int j = 0; j < maxCol; j++) {
                rowArray.add(Optional.empty());
            }

            pieceGrid.add(rowArray);
        }
    }

    protected void addPiecesToBoard(List<Piece> pieces) throws OutOfBoardException {
        for(Piece piece : pieces) {
            if(piece.getPosition().checkInBounds()) {
                int rowVal = piece.getRow();
                int colVal = piece.getCol();

                pieceGrid.get(rowVal).set(colVal, Optional.of(piece));
            } else {
                throw new OutOfBoardException("Error: Tried to add a piece that is out of bounds to a board.");
            }
        }
    }

    /**
     * Gets the maximum number of rows for this board.
     * @return integer guaranteed to be at least 1
     */
    public abstract int getBoardMaxRow();

    /**
     * Gets the maximum number of rows for this board.
     * @return integer gruaranteed to be at least 1
     */
    public abstract int getBoardMaxCol();

    @Override
    public String toString() {
        String str = "";
        for(int i = 0; i < maxRow; i++) {
            for(int j = 0; j < maxCol; j++) {
                str = str + pieceGrid.get(i).get(j).orElse(new EmptyPiece()).toString() + "  ";
            }
            str = str + "\n";
        }

        return str;
    }
}
