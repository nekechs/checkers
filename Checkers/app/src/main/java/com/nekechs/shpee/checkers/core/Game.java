package com.nekechs.shpee.checkers.core;

import android.graphics.Paint;
import android.graphics.PostProcessor;

import com.nekechs.shpee.checkers.core.vectors.PositionVector;
import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<CaptureMove> possibleCaptureMoves = testCaptureMoves();
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
    public List<CaptureMove> getCaptureMovesAtPosition(PositionVector piecePosition) {
        CaptureMove[] moveList = {new CaptureMove(piecePosition, VectorFactory.Direction.NORTHEAST),
                                    new CaptureMove(piecePosition, VectorFactory.Direction.NORTHWEST),
                                    new CaptureMove(piecePosition, VectorFactory.Direction.SOUTHEAST),
                                    new CaptureMove(piecePosition, VectorFactory.Direction.SOUTHWEST)};

        return Arrays.stream(moveList)
                .filter(captureMove -> boards.peek().producePossibleBoard(captureMove).isPresent())
                .collect(Collectors.toList());
    }

    public void testBoardDuplication() {
        Board board = new Board(boards.peek());
        boards.push(board);
    }

    private void progressMove() {
        currentMoveNumber++;

    }

    public List<CaptureMove> testCaptureMoves() {
        return boards.peek().allPieceStates.stream()
                .flatMap(pieceState -> getCaptureMovesAtPosition(pieceState.getPosition()).stream())
                .collect(Collectors.toList());
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
