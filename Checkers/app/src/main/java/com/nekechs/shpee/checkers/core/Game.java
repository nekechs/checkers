package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.PositionVector;
import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public void undoMove() {
        if(boards.size() > 1) {
            boards.pop();
            currentMoveNumber--;
        }
    }

    public GameState processMoveRequest(Move move) {
        List<Move> possibleCaptureMoves = testCaptureMoves();
        if(!(move instanceof CaptureMove) && !possibleCaptureMoves.isEmpty()) {
            return GameState.ILLEGALMOVE;
        }

        Optional<Board> newBoard = boards.peek().producePossibleBoard(move);
        if(newBoard.isPresent()) {
            currentMoveNumber++;
            boards.push(newBoard.get());

            return GameState.NORMALMOVE;
        } else {
            return GameState.ILLEGALMOVE;
        }
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


    //TODO: Make sure this works
    // Status so far: Works
    public List<Move> getInitialCaptureMoves(PositionVector piecePosition) {
        CaptureMove[] moveList = {new CaptureMove(piecePosition, VectorFactory.Direction.NORTHEAST),
                                    new CaptureMove(piecePosition, VectorFactory.Direction.NORTHWEST),
                                    new CaptureMove(piecePosition, VectorFactory.Direction.SOUTHEAST),
                                    new CaptureMove(piecePosition, VectorFactory.Direction.SOUTHWEST)};

        return testMoves(moveList);
    }

    public List<Move> getNormalMoves(PositionVector piecePosition) {
        NormalMove[] moveList = {new NormalMove(piecePosition, VectorFactory.Direction.NORTHEAST),
                new NormalMove(piecePosition, VectorFactory.Direction.NORTHWEST),
                new NormalMove(piecePosition, VectorFactory.Direction.SOUTHEAST),
                new NormalMove(piecePosition, VectorFactory.Direction.SOUTHWEST)};

        return testMoves(moveList);
    }

    public List<Move> testMoves(Move[] moveList) {
        return Arrays.stream(moveList)
                .filter(move -> boards.peek().producePossibleBoard(move).isPresent())
                .collect(Collectors.toList());
    }

    /**
     * Get the list of all single movement capture moves that are present in a given board position.
     * Useful primarily for checking whether or not capture moves exist, so that all non-capture
     * moves can be disabled.
     * @return A list containing any number of CaptureMoves if they exist for a given board, or an empty list if none exist
     */
    public List<Move> testCaptureMoves() {
        return boards.peek().allPieceStates.stream()
                .flatMap(pieceState -> getInitialCaptureMoves(pieceState.getPosition()).stream())
                .collect(Collectors.toList());
    }

    public Map<PositionVector, Move> getAllValidMovesAtPosition(PositionVector position) {
        Map<PositionVector, Move> moveListMap = new HashMap<PositionVector, Move>();

        if(testCaptureMoves().isEmpty()) {
            // We are in normal move mode: We should only get normal moves.
            moveListMap = getNormalMoves(position).stream()
                    .collect(Collectors.toMap(Move::getFinalSpot, Function.identity()));
        } else {
            // We are in capture move mode; Only get capture moves.
            //TODO: Find a way to get a list of all capture moves. Not just the prelim ones.
            // WOrk on this. NOT FINISHED!!!!!!!!!


        }

        return moveListMap;
    }

    public void printLatestPieceList() {
//        System.out.println(boards.peek().getPieceCoordinates());
        boards.stream()
                .forEach(board -> System.out.println(board.getPieceCoordinates()));
    }

    public String toString() {
        return boards.peek().toString();
    }
}
