//package com.nekechs.shpee.checkers.core;
//
//import com.nekechs.shpee.checkers.core.vectors.PositionVector;
//
//import java.util.List;
//import java.util.Optional;
//
//public class MoveFactory {
//    /**
//     *  The generateMove methods below are methods that return a type of Move that satisfy the
//     *  following requirements:
//     *  <ul>
//     *      <li>The move exists within the confines of the 8x8 board</li>
//     *      <li>The move consists of diagonal movements</li>
//     *      <li>The move either moves diagonally by either 1 or 2</li>
//     *  </ul>
//     *
//     *  These methods do <em>not</em> do any checks for validity, like whether or not it is legal
//     *  for a given piece, whether or not the move tries to capture its own team, or whether or not
//     *  the move tries to do a capture move whenever a capture is not possible.
//     */
//
//    public Optional<Move> parseMove(List<PositionVector> positionVectorList) {
//        // This is not even a valid move to begin with.
//        if(positionVectorList.size() < 2) {
//            return Optional.empty();
//        }
//
//        // This means that this is "supposed" to be a move normal move. This means that the magnitude
//        // has to be equal to 1, and it has to be a diagonal direction. If it is not, then whoops.
//        if(positionVectorList.size() == 3) {
//
//        }
//        PositionVector initialPosition = positionVectorList.get(0);
//
////        if()
//    }
////    public abstract Move generateMove(PositionVector startingPosition, List<MoveVector> moveVectorList);
//}
