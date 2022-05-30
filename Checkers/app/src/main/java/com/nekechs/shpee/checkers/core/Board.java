package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.exceptions.OutOfBoardException;
import com.nekechs.shpee.checkers.core.vectors.PositionVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Board {
    final Piece[][] grid = new Piece[8][8];

    static final PositionVector WHITE_RIGHT_CORNER = new PositionVector(7,7);
    static final PositionVector BLACK_RIGHT_CORNER = new PositionVector(0,0);

    static final PositionVector[] whiteStartingPositions = {
            new PositionVector(7,0),
            new PositionVector(5,0),
            new PositionVector(7,2),
            new PositionVector(5,2),
            new PositionVector(7,4),
            new PositionVector(5,4),
            new PositionVector(7,6),
            new PositionVector(5,6),
            new PositionVector(6,1),
            new PositionVector(6,3),
            new PositionVector(6,5),
            new PositionVector(6,7),
    };

    static final PositionVector[] blackStartingPosition = {
            new PositionVector(2,1),
            new PositionVector(0,1),
            new PositionVector(2,3),
            new PositionVector(0,3),
            new PositionVector(2,5),
            new PositionVector(0,5),
            new PositionVector(2,7),
            new PositionVector(0,7),
            new PositionVector(1,0),
            new PositionVector(1,2),
            new PositionVector(1,4),
            new PositionVector(1,6)
    };

    Game game;
    int moveNumber;

    public Board(Game game) {
        this.game = game;
        moveNumber = game.currentMoveNumber;

        List<Piece> pieceList = new ArrayList<>();
        for(PositionVector vect : whiteStartingPositions) {
            pieceList.add(new CheckersPawn(vect, game.white));
        }

        for(PositionVector vect : blackStartingPosition) {
            pieceList.add(new CheckersPawn(vect, game.black));
        }

        try{
            addPiecesToBoard(pieceList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Board(Board b) {
        this.game = b.game;
        this.moveNumber = b.moveNumber;

        try{
            addPiecesToBoard(game.white.pieceList);
            addPiecesToBoard(game.black.pieceList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addPiecesToBoard(List<Piece> pieces) throws OutOfBoardException {
        for(Piece piece : pieces) {
            if(piece.getPosition().checkInBounds()) {
                int rowVal = piece.getRow();
                int colVal = piece.getCol();

//                pieceGrid.get(rowVal).set(colVal, Optional.of(piece));
                grid[rowVal][colVal] = piece;
            } else {
                throw new OutOfBoardException("Error: Tried to add a piece that is out of bounds to a board.");
            }
        }
    }

    public Optional<Piece> getPieceAtPosition(PositionVector position) {
        int rowVal = position.getRow();
        int colVal = position.getCol();

        Piece piece = grid[rowVal][colVal];

        return piece == null ? Optional.empty() : Optional.of(piece);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
//                str = str + pieceGrid.get(i).get(j).orElse(new EmptyPiece()).toString() + "  ";
                str.append(grid[i][j] == null ? "  " : grid[i][j].toString());
            }
            str.append("\n");
        }

        return str.toString();
    }
}
