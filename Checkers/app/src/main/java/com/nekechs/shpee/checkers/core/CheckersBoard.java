package com.nekechs.shpee.checkers.core;

import java.util.List;

public class CheckersBoard extends Board{

    static final PositionVector WHITE_RIGHT_CORNER = new PositionVector(7,7);
    static final PositionVector BLACK_RIGHT_CORNER = new PositionVector(0,0);



    public CheckersBoard(List<Piece> pieceList) {
        super(8,8);
        Team white = new Team(this, 'w', WHITE_RIGHT_CORNER);
        Team black = new Team(this, 'w', BLACK_RIGHT_CORNER);


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
