package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.Movement;
import com.nekechs.shpee.checkers.core.vectors.PositionVector;

import java.util.Optional;
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
        white = new Team('w', Board.WHITE_RIGHT_CORNER, 1);
        black = new Team('b', Board.BLACK_RIGHT_CORNER, 0);

        boards = new Stack<Board>();
        boards.push(new Board(this));

        currentMoveNumber = 0;

    }

    public GameState processMoveRequest(Move move) {
        PositionVector startingPoint = move.getStartingSpot();
        Optional<Piece> possiblePiece = boards.peek().getPieceAtPosition(startingPoint);

        if(!possiblePiece.isPresent()) {
            // No piece is present at the square you start at; Illegal move!!!!
            return GameState.ILLEGALMOVE;
        }

        Piece piece = possiblePiece.get();

        if(move instanceof NormalMove) {
            Board newBoard = new Board(boards.peek());
            NormalMove normalMove = (NormalMove) move;
            if(!piece.isValidMoveDirection(normalMove.moveDirection) ) {
                return GameState.ILLEGALMOVE;
            }

            PositionVector finalPosition = startingPoint.addDirection(normalMove.moveDirection, Movement.MOVEMENT_DISTANCE.SINGLE);
            Optional<Piece> possibleDestination = newBoard.getPieceAtPosition(finalPosition);

            if(possibleDestination.isPresent()) {
                // This means that we are trying to make a normal move into a spot where a piece
                // already exists, whether it's our own piece or it an enemy piece. That is illegal.
                return GameState.ILLEGALMOVE;
            }

            // Swap the values!!
            newBoard.grid[finalPosition.getRow()][finalPosition.getCol()] = piece;
            newBoard.grid[startingPoint.getRow()][startingPoint.getCol()] = null;

            currentMoveNumber++;
            boards.push(newBoard);
            return GameState.NORMALMOVE;
        }

        return GameState.ILLEGALMOVE;
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
