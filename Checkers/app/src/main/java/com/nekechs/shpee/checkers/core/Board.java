package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.exceptions.OutOfBoardException;
import com.nekechs.shpee.checkers.core.vectors.Movement;
import com.nekechs.shpee.checkers.core.vectors.PositionVector;
import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Board {
    final Piece[][] grid = new Piece[8][8];
    final List<PieceState> allPieceStates;

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

    Game game;
    final int moveNumber;

    public Board(Game game) {
        this.game = game;
        this.allPieceStates = new LinkedList<>();
        moveNumber = game.currentMoveNumber;

        for(PositionVector vect : whiteStartingPositions) {
            allPieceStates.add(new PieceState(new CheckersPawn(game.white), vect));
        }

        for(PositionVector vect : blackStartingPosition) {
            allPieceStates.add(new PieceState(new CheckersPawn(game.black), vect));
        }

//        System.out.println(game.white.pieceList);
//        System.out.println(game.black.pieceList);

        addPiecesToBoard(allPieceStates);
    }

    public Board(Board b) {
        this.game = b.game;
        this.moveNumber = game.currentMoveNumber + 1;
//        this.allPieceStates = new LinkedList<>(b.allPieceStates);
        this.allPieceStates = b.allPieceStates.stream()
                        .map(PieceState::new)
                        .collect(Collectors.toList());

//        addPiecesToBoard(game.white.pieceList);
//        addPiecesToBoard(game.black.pieceList);
        addPiecesToBoard(this.allPieceStates);

//        System.out.println("Duping board");
    }

    public Optional<Board> producePossibleBoard(Move move) {
        //TODO: Make a method that can produce a board given a move and a certain board on which the
        // will be made.

        PositionVector startingPoint = move.getStartingPosition();
        Optional<Piece> possiblePiece = getPieceAtPosition(startingPoint);

        Team whoseTurn = game.getWhoseTurn();

        if(!possiblePiece.isPresent() || !possiblePiece.get().team.equals(whoseTurn) || !move.isPlausible()) {
            // No piece is present at the square you start at, or the move isn't plausible; Illegal move!!!!
            return Optional.empty();
        }

//        System.out.println("CURRENT TURN: " + whoseTurn.teamColor);

        Piece piece = possiblePiece.get();

        Board newBoard = new Board(this);

        if(move instanceof NormalMove) {
            NormalMove normalMove = (NormalMove) move;
            if(!piece.isValidMoveDirection(normalMove.moveDirection) ) {
                return Optional.empty();
            }

            PositionVector finalPosition = startingPoint.addDirection(normalMove.moveDirection, Movement.MOVEMENT_DISTANCE.SINGLE);
            Optional<Piece> possibleDestination = newBoard.getPieceAtPosition(finalPosition);

            if(possibleDestination.isPresent()) {
                // This means that we are trying to make a normal move into a spot where a piece
                // already exists, whether it's our own piece or it an enemy piece. That is illegal.
                return Optional.empty();
            }

            // Swap the values!!
//            piece.setPosition(finalPosition);
            newBoard.setPiecePosition(piece, finalPosition);

            newBoard.grid[finalPosition.getRow()][finalPosition.getCol()] = piece;
            newBoard.grid[startingPoint.getRow()][startingPoint.getCol()] = null;

            if(move.isPromotionAttempt(whoseTurn)) {
                newBoard.promotePieceAtPosition(finalPosition);
            }

            return Optional.of(newBoard);
        }

        if(move instanceof CaptureMove) {
            CaptureMove captureMove = (CaptureMove) move;

            PositionVector currentDestination = startingPoint;
            PositionVector previousDestination;

            for(VectorFactory.Direction direction : captureMove.movementSequence) {
                if(!piece.isValidMoveDirection(direction)) {
                    return Optional.empty();
                }

                PositionVector captureSquare = currentDestination.addDirection(direction, Movement.MOVEMENT_DISTANCE.SINGLE);

                previousDestination = currentDestination;
                currentDestination = currentDestination.addDirection(direction, Movement.MOVEMENT_DISTANCE.DOUBLE);

                Optional<Piece> possibleCapturedPiece = newBoard.getPieceAtPosition(captureSquare);
                Optional<Piece> possibleDestinationPiece = newBoard.getPieceAtPosition(currentDestination);

                if(possibleDestinationPiece.isPresent() ||
                        !possibleCapturedPiece.isPresent() ||
                        possibleCapturedPiece.get().team.equals(whoseTurn)) {
                    // Either something exists where we want to go, something does not exist for us
                    // to capture, or the piece to be captured is on our own team. This is not meant
                    // to happen, and the move is illegal.
                    return Optional.empty();
                }

                newBoard.grid[currentDestination.getRow()][currentDestination.getCol()] = piece;
                newBoard.grid[captureSquare.getRow()][captureSquare.getCol()] = null;
                newBoard.grid[previousDestination.getRow()][previousDestination.getCol()] = null;
                newBoard.setPiecePosition(piece, currentDestination);
//                piece.setPosition(currentDestination);

//                possibleCapturedPiece.get().setPosition(new PositionVector(-1,-1));
                newBoard.setPieceCaptured(possibleCapturedPiece.get());
            }

            // Note: Horribly optimized. TODO: Make this more optimized.
            if(move.isPromotionAttempt(whoseTurn)) {
                newBoard.promotePieceAtPosition(move.getFinalSpot());
            }

            return Optional.of(newBoard);
        }

        return Optional.empty();
    }

    public void addPiecesToBoard(List<PieceState> pieces) {
        for(PieceState pieceState : pieces) {

            if(pieceState.getPosition().checkInBounds()) {
                int rowVal = pieceState.getPosition().getRow();
                int colVal = pieceState.getPosition().getCol();

//                pieceGrid.get(rowVal).set(colVal, Optional.of(piece));
                grid[rowVal][colVal] = pieceState.getPiece();
            }
        }
    }

    public Optional<Piece> getPieceAtPosition(PositionVector position) {
        if(!position.checkInBounds()) {
            return Optional.empty();
        }

        int rowVal = position.getRow();
        int colVal = position.getCol();

        Piece piece = grid[rowVal][colVal];

        return piece == null ? Optional.empty() : Optional.of(piece);
    }

    public boolean pieceExistsAtPosition(PositionVector positionVector) {
        return grid[positionVector.getRow()][positionVector.getCol()] != null;
    }

    /**
     * Finds a piece in the list of pieceStates, and sets the position based on that piece state
     * that was found.
     * @param piece Piece that you want to change the position of
     * @param position The position that you want the piece to assume
     */
    public void setPiecePosition(Piece piece, PositionVector position) {
        Optional<PieceState> pieceState = findStateOfPiece(piece);

        pieceState.ifPresent(state -> state.setPiecePosition(position));
    }

    /**
     * Make the piece that is specified classify as captured. Essentially, this sets the position
     * field of the piece that is captured to empty.
     * @param piece The piece that is to be made as captured.
     */
    public void setPieceCaptured(Piece piece) {
        Optional<PieceState> pieceState = findStateOfPiece(piece);

        pieceState.ifPresent(PieceState::makePieceCaptured);
    }

    private Optional<PieceState> findStateOfPiece(Piece piece) {
        return allPieceStates.stream()
                .filter(state -> state.getPiece().equals(piece))
                .findFirst();
    }

    void promotePieceAtPosition(PositionVector piecePosition) {
        Optional<Piece> piece = getPieceAtPosition(piecePosition);

        // Check if a checkers pawn exists on the board on the given position
        if(piece.isPresent() && piece.get() instanceof CheckersPawn) {
            //TODO: Find a way to promote the piece at the position and the piece in the piece list

            // This piece is created because we need a new king piece on the board. This needs to be
            // put into BOTH the checkers grid and the list of piece states.
            Piece kingPiece = new CheckersKing(piece.get().team);

            setPiecePosition(kingPiece, piecePosition);

            // Find the state where the piece is held and make king go there
            findStateOfPiece(piece.get())
                    .ifPresent(pieceState -> pieceState.changePiece(kingPiece));
        }
    }

    public String getPieceCoordinates() {
        return allPieceStates.toString();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < 8; i++) {
            str.append((-i + 8) + "\t");
            for(int j = 0; j < 8; j++) {
//                str = str + pieceGrid.get(i).get(j).orElse(new EmptyPiece()).toString() + "  ";
                str.append(grid[i][j] == null ? ( (i + j) % 2 == 0 ? "  " : "##") : grid[i][j].toString());
            }
            str.append("\n");
        }

        char[] coordList = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

        str.append("\t ");
        for(char character : coordList) {
            str.append(character).append(" ");
        }

        str.append("\n");

        str.append(allPieceStates.toString()).append("\n");

        return str.toString();
    }
}
