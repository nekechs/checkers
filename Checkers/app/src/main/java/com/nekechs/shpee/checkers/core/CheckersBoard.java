package com.nekechs.shpee.checkers.core;

import java.util.ArrayList;
import java.util.List;

public class CheckersBoard extends Board{

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

    Team white;
    Team black;


    public CheckersBoard() {
        super(8,8);
        white = new Team(this, 'w', WHITE_RIGHT_CORNER);
        black = new Team(this, 'b', BLACK_RIGHT_CORNER);

        List<Piece> pieceList = new ArrayList<>();
        for(PositionVector vect : whiteStartingPositions) {
            pieceList.add(new CheckersPawn(vect, white));
        }

        for(PositionVector vect : blackStartingPosition) {
            pieceList.add(new CheckersPawn(vect, black));
        }

        try{
            addPiecesToBoard(pieceList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getBoardMaxCol() {
        return super.maxCol;
    }

    @Override
    public int getBoardMaxRow() {
        return super.maxRow;
    }
}
