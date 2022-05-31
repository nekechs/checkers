package com.nekechs.shpee.checkers.core;

import android.graphics.Paint;

import com.nekechs.shpee.checkers.core.vectors.Movement;
import com.nekechs.shpee.checkers.core.vectors.PositionVector;
import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

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

        if(!possiblePiece.isPresent() || possiblePiece.get().team.equals(getWhoseTurn())) {
            // No piece is present at the square you start at; Illegal move!!!!
            return GameState.ILLEGALMOVE;
        }

        Piece piece = possiblePiece.get();

        if(move instanceof NormalMove) {
            System.out.println("this be normal move");
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

            System.out.println("Direction: " + normalMove.moveDirection + "; Final position: " + finalPosition + "; Starting position: " + startingPoint);

            // Swap the values!!
            piece.setPosition(finalPosition);
            newBoard.grid[finalPosition.getRow()][finalPosition.getCol()] = piece;
            newBoard.grid[startingPoint.getRow()][startingPoint.getCol()] = null;

            currentMoveNumber++;
            boards.push(newBoard);
            return GameState.NORMALMOVE;
        }

        //TODO: Fix the issue where capturing moves get classified as "NormalMove" which is incorrect
        if(move instanceof CaptureMove) {
            Board board = new Board(boards.peek());
            CaptureMove captureMove = (CaptureMove) move;

            PositionVector currentDestination = startingPoint;
            PositionVector previousDestination;

            System.out.println("Capture move size: " + captureMove.movementSequence.size());

            for(VectorFactory.Direction direction : captureMove.movementSequence) {
                System.out.println("HuhH???");

                if(!piece.isValidMoveDirection(direction)) {
//                    System.out.println("bruh");
                    return GameState.ILLEGALMOVE;
                }

                PositionVector captureSquare = currentDestination.addDirection(direction, Movement.MOVEMENT_DISTANCE.SINGLE);

                previousDestination = currentDestination;
                currentDestination = currentDestination.addDirection(direction, Movement.MOVEMENT_DISTANCE.DOUBLE);

                Optional<Piece> possibleCapturedPiece = board.getPieceAtPosition(captureSquare);
                Optional<Piece> possibleDestinationPiece = board.getPieceAtPosition(currentDestination);

                if(possibleDestinationPiece.isPresent() ||
                        !possibleCapturedPiece.isPresent() ||
                        possibleCapturedPiece.get().team.equals(getWhoseTurn())) {
                    // Either something exists where we want to go, something does not exist for us
                    // to capture, or the piece to be captured is on our own team. This is not meant
                    // to happen, and the move is illegal.
//                    System.out.println("momentoooo");
                    return GameState.ILLEGALMOVE;
                }

                board.grid[currentDestination.getRow()][currentDestination.getCol()] = piece;
                board.grid[captureSquare.getRow()][captureSquare.getCol()] = null;
                board.grid[previousDestination.getRow()][previousDestination.getCol()] = null;
                piece.setPosition(currentDestination);

                // This is an indicator that the piece is captured.
                currentMoveNumber++;

                possibleCapturedPiece.get().setPosition(new PositionVector(-1,-1));
                boards.push(board);
            }

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

    public void testBoardDuplication() {
        Board board = new Board(boards.peek());
        boards.push(board);
    }

    private void progressMove() {
        currentMoveNumber++;

    }

    public String toString() {
        return boards.peek().toString();
    }
}
