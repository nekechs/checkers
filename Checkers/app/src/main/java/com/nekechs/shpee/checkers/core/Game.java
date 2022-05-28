package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.PositionVector;

import java.util.Stack;
public class Game {
    Stack<Board> boards;
    int currentMoveNumber;

    enum GameState {
        DRAW,
        RESIGN,
        STALEMATE,
        CHECKMATE,
        ILLEGALMOVE,
        NORMALMOVE
    }

    final Team white;
    final Team black;

    public Game() {
        boards = new Stack<Board>();
        boards.push(new Board(this));

        currentMoveNumber = 0;

        white = new Team('w', Board.WHITE_RIGHT_CORNER, 1);
        black = new Team('b', Board.BLACK_RIGHT_CORNER, 0);

    }

    public void processMoveRequest(Move move) {
        PositionVector startingPoint = move.getStartingSpot();
    }

    public Team getWhoseTurn() /*throws NoTeamFoundException*/ {
        //TODO: Evaluate whether or not this (possibly commented) style of code with the exceptions is good design
//        Team[] teams = new Team[2];
//        teams[0] = white;
//        teams[1] = black;
//
//        for(Team team : teams) {
//            if(team.startingMoveNumber == inGameTurn % 2) {
//                return team;
//            }
//        }
//
//        // Very bad. Do not get to this point, or else something has gone horribly wrong.
//        throw new NoTeamFoundException("Critical issue: Could not find any team existing that goes for this turn.")

        if(white.startingMoveNumber == currentMoveNumber % 2) {
            return white;
        }

        return black;
    }

    private void progressMove() {
        currentMoveNumber++;

    }

    public String toString() {
        return boards.peek().toString();
    }
}
