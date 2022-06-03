package com.nekechs.shpee.checkers.core;

import com.nekechs.shpee.checkers.core.vectors.Movement;
import com.nekechs.shpee.checkers.core.vectors.PositionVector;
import com.nekechs.shpee.checkers.core.vectors.RelativeVector;
import com.nekechs.shpee.checkers.core.vectors.VectorFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MoveParser {

    /**
     * Takes a string of location coordinates (e1, g8, b4, etc.) and converts them into a list of
     * position vectors corresponding to said coordinates.
     * <p>
     * Example:
     * "a1 h8 f3" returns position vectors (0, 7), (7, 0), (5, 5)
     * <p>
     * If any of the coordinates are not valid, then this will simply return an EMPTY list. It will
     * not return null.
     * @param input a string that is supposed to be a list of positions on a chess/checkers board
     * @return A list (probably linked list) that contains all of the PositionVector values
     */
    public static List<PositionVector> parseTextMoveList(String input) {
        String[] positionStrings = input.split(" ");
        List<PositionVector> positionVectorList = new LinkedList<>();

        for(String positionString : positionStrings) {
            PositionVector vector = textToPositionVector(positionString);
            if(vector == null) {
                return new LinkedList<>();
            }

            positionVectorList.add(vector);
        }
//        System.out.println(positionVectorList);
        return positionVectorList;
    }

    protected static PositionVector textToPositionVector(String text) {
        text = text.toLowerCase();
        if(text.length() != 2) {
            return null;
        }

        char rowChar = text.charAt(0);
        char colChar = text.charAt(1);

        int rowVal = 0;
        int colVal = 0;

        // Take advantage of the fact that a-h are sequential integers in ASCII
        // And the fact that 97 is the where 'a' is located in ASCII
        rowVal = (int)rowChar - 97;

        if(rowVal > 7 || rowVal < 0) {
            //row value is out of bounds!!
            return null;
        }

        int colEntry = (int) colChar - 48;
        if(colEntry > 8 || colEntry < 1) {
            return null;
        }

        // linear mapping where 1 -> 7 and
        colVal = (-colEntry) + 8;

        return new PositionVector(colVal, rowVal);
    }
    /**
     *  The generateMove methods below are methods that return a type of Move that satisfy the
     *  following requirements:
     *  <ul>
     *      <li>The move exists within the confines of the 8x8 board</li>
     *      <li>The move consists of diagonal movements</li>
     *      <li>The move either moves diagonally by either 1 or 2</li>
     *  </ul>
     *
     *  These methods do <em>not</em> do any checks for validity, like whether or not it is legal
     *  for a given piece, whether or not the move tries to capture its own team, or whether or not
     *  the move tries to do a capture move whenever a capture is not possible.
     * @param positionVectorList a list of all of the positions on the board that the move specifies
     * @return Optionally a move that actually comes from the list of position vectors. If the list
     * of positions on the board does not represent a valid move, then this returns an empty optional.
     */
    public static Optional<Move> parseMove(List<PositionVector> positionVectorList) {
//        System.out.println(positionVectorList);

        // This is not even a valid move to begin with.
        if(positionVectorList.size() < 2) {
            return Optional.empty();
        }

        PositionVector startingPosition = positionVectorList.get(0);
        if(!startingPosition.checkInBounds()) {
//            System.out.println("target spotted 1");
            return Optional.empty();
        }

        //TODO: Make sure that this code works;

        // This means that this is "supposed" to be a move normal move. This means that the magnitude
        // has to be equal to 1, and it has to be a diagonal direction. If it is not, then whoops.
        if(positionVectorList.size() == 2) {
            PositionVector finalPosition = positionVectorList.get(1);
            if(!finalPosition.checkInBounds()) {
                return Optional.empty();
            }

            RelativeVector moveVector = finalPosition.minusVector(startingPosition);
//            System.out.println(moveVector);

            Optional<Movement> possibleMovement = Movement.possibleVectorToMovement(moveVector);
            if(possibleMovement.isPresent()) {
                Movement movement = possibleMovement.get();
                return movement.getDistance() == Movement.MOVEMENT_DISTANCE.SINGLE ?
                        Optional.of(new NormalMove(startingPosition, movement.getDirection())) :
                        Optional.of(
                                new CaptureMove(startingPosition, new LinkedList<>(List.of(movement.getDirection()))));
//                if (movement.getDistance() == Movement.MOVEMENT_DISTANCE.SINGLE) {
//                    return Optional.of(new NormalMove(startingPosition, movement.getDirection()));
//                }
            }
            return Optional.empty();
        }

        List<VectorFactory.Direction> directionList = new LinkedList<>();
        PositionVector latestPosition = startingPosition;

        for(int i = 1; i < positionVectorList.size(); i++) {
            PositionVector position = positionVectorList.get(i);
            if(!position.checkInBounds()) {
                return Optional.empty();
            }

            RelativeVector moveVector = position.minusVector(latestPosition);
            Optional<Movement> possibleMovement = Movement.possibleVectorToMovement(moveVector);
            if(possibleMovement.isPresent()) {
                Movement movement = possibleMovement.get();
                if(movement.getDistance() == Movement.MOVEMENT_DISTANCE.DOUBLE) {
                    directionList.add(movement.getDirection());
                } else {
                    return Optional.empty();
                }
            }

            latestPosition = position;
        }

        return Optional.of(new CaptureMove(startingPosition, directionList));
    }
//    public abstract Move generateMove(PositionVector startingPosition, List<MoveVector> moveVectorList);
}
