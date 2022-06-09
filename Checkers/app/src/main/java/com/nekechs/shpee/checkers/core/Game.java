package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.PositionVector;
import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Game implements Serializable {
    Stack<Board> boards;
    public List<Move> moveList;

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

    private GameListener listener;

    public Game() {
        white = new Team('w', Board.WHITE_RIGHT_CORNER, 1);
        black = new Team('b', Board.BLACK_RIGHT_CORNER, 0);

        boards = new Stack<Board>();
        boards.push(new Board(this));
        moveList = new ArrayList<>();

        currentMoveNumber = 0;
    }

    public void setListener(GameListener listener) {
        this.listener = listener;
    }

    public interface GameListener {
        default public void onIllegalMove() {

        }

        default public void onCheckmate() {

        }

        default public void onStalemate() {

        }

        default public void onNormalMove() {

        }

        default public void onCaptureMove() {

        }
    }

    public void undoMove() {
        if(boards.size() > 1) {
            boards.pop();
            currentMoveNumber--;
            moveList.remove(moveList.size() - 1);
        }
    }

    public void resetGame() {
        boards = new Stack<>();
        currentMoveNumber = 0;
        boards.push(new Board(this));
        moveList = new ArrayList<>();
    }

    public GameState processMoveRequest(Move move) {
        List<Move> possibleCaptureMoves = testCaptureMoves();
        if(!(move instanceof CaptureMove) && !possibleCaptureMoves.isEmpty()) {
            return GameState.ILLEGALMOVE;
        }

        Optional<Board> newBoard = boards.peek().producePossibleBoard(move);
        if(newBoard.isPresent()) {
            // An actual move is present... Wow!
            currentMoveNumber++;
            boards.push(newBoard.get());
            moveList.add(move);

            List<PositionVector> remainingPiecePositions = boards.peek().allPieceStates.stream()
                    .filter(state -> !state.isCaptured() && state.getPiece().team.equals(getWhoseTurn()))
                    .map(PieceState::getPosition)
                    .collect(Collectors.toList());

            if(remainingPiecePositions.isEmpty()) {
                // There are no remaining moves for the team; win by checkmate!
                listener.onCheckmate();
                return GameState.CHECKMATE;
            } else if(remainingPiecePositions.stream()
                    .flatMap(positionVector -> getAllValidMovesAtPosition(positionVector).keySet().stream())
                    .count() == 0){
                // There are no moves!
                listener.onStalemate();
                return GameState.STALEMATE;
            }

            // Properly report whether or not the move was a capture move or a normal move
            if(move instanceof NormalMove) listener.onNormalMove();
            if(move instanceof CaptureMove) listener.onCaptureMove();
            return GameState.NORMALMOVE;
        } else {
            return GameState.ILLEGALMOVE;
        }
    }

    /**
     * Method that returns a list of strings that represent the notation of an entire game. This is
     * specifically made so that a ListView can interface with this and have a presentable list of
     * moves to show.
     * @param moveList List of moves that is to be converted to the list of strings
     * @return A list of strings that displays the entire list of moves throughout the whole game.
     */
    public static List<String> produceMoveStringList(List<Move> moveList) {
        List<String> moveStringList = new ArrayList<>();

        for(int i = 0; i < moveList.size(); i += 2) {
            String str = moveList.get(i).getPositionNotation() +
                    ((i + 1) < moveList.size() ? "; " + moveList.get(i + 1).getPositionNotation() : "");

            moveStringList.add(str);
        }

        return moveStringList;
    }

    public Team getWhoseTurn() /*throws NoTeamFoundException*/ {
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
            Queue<CaptureMove> moveQueue = new LinkedList<>();
            moveQueue.add(new CaptureMove(position, VectorFactory.Direction.NORTHEAST));
            moveQueue.add(new CaptureMove(position, VectorFactory.Direction.NORTHWEST));
            moveQueue.add(new CaptureMove(position, VectorFactory.Direction.SOUTHEAST));
            moveQueue.add(new CaptureMove(position, VectorFactory.Direction.SOUTHWEST));

            while(!moveQueue.isEmpty()) {
                CaptureMove currentMove = moveQueue.remove();

                if(boards.peek().producePossibleBoard(currentMove).isPresent()) {
                    moveListMap.put(currentMove.getFinalSpot(), currentMove);

                    moveQueue.add(new CaptureMove(currentMove, VectorFactory.Direction.NORTHEAST));
                    moveQueue.add(new CaptureMove(currentMove, VectorFactory.Direction.NORTHWEST));
                    moveQueue.add(new CaptureMove(currentMove, VectorFactory.Direction.SOUTHEAST));
                    moveQueue.add(new CaptureMove(currentMove, VectorFactory.Direction.SOUTHWEST));
                }
            }
        }

        return moveListMap;
    }

    public void printLatestPieceList() {
//        System.out.println(boards.peek().getPieceCoordinates());
        boards.forEach(board -> System.out.println(board.getPieceCoordinates()));
    }

    /**
     * Gets a list of
     * @return
     */
    public List<PieceState> getLatestPieceList() {
        return new ArrayList<>(boards.peek().allPieceStates);
    }

    public boolean pieceAtPositionCanMove(PositionVector piecePosition) {
        Optional<Piece> piece = boards.peek().getPieceAtPosition(piecePosition);
        return piece.isPresent() && piece.get().team.equals(getWhoseTurn());
    }

    public String toString() {
        return boards.peek().toString();
    }
}
