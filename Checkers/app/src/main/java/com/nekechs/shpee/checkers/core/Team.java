
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
    final PositionVector teamOrigin;
    final MoveVector.Direction movementDirection;

    final List<Piece> pieceList;

    public Team(char teamColor, List<Piece> existingPieceList, PositionVector teamOrigin) {
        this.teamColor = teamColor;

        this.pieceList = existingPieceList;

        this.teamOrigin = teamOrigin;

        this.movementDirection = getDirection(teamOrigin);
    }

    public Team(char teamColor, PositionVector teamOrigin) {
        this(teamColor, new ArrayList<>(), teamOrigin);
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

    protected static MoveVector.Direction getDirection(PositionVector teamOrigin) {
        int rowVal = teamOrigin.row;
        int colVal = teamOrigin.col;

        if(rowVal < 4) {
            if(colVal < 4) {
                return MoveVector.Direction.SOUTH;
            } else {
                return MoveVector.Direction.WEST;
            }
        } else {
            if(colVal < 4) {
                return MoveVector.Direction.EAST;
            } else {
                return MoveVector.Direction.NORTH;
            }
        }
    }
}