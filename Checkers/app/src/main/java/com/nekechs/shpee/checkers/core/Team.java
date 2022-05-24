package com.nekechs.shpee.checkers.core;

import java.util.List;
import java.util.ArrayList;

/**
 * Used in order to keep track of the teams on a given board game. Most likely, the teams will be
 * white and black (most common in checkers/chess).
 *
 * @author nekechs
 */
public class Team {
    final char teamColor;
    final Board board;
    final PositionVector teamOrigin;

    final List<Piece> pieceList;

    public Team(Board board, char teamColor, List<Piece> existingPieceList, PositionVector teamOrigin) {
        this.board = board;
        this.teamColor = teamColor;

        this.pieceList = existingPieceList;

        this.teamOrigin = teamOrigin;
    }

    public Team(Board board, char teamColor, PositionVector teamOrigin) {
        this(board, teamColor, new ArrayList<>(), teamOrigin);
    }

    public void addPiece(Piece piece) {
        pieceList.add(piece);
    }

    public char getTeamColor() {
        return teamColor;
    }

    public int getTeamRowOrigin() {
        return teamOrigin.getRow();
    }

    public PositionVector getTeamOrigin() {
        return teamOrigin;
    }

//    public MoveVector getDirection() {
//
//    }
}
