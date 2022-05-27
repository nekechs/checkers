package com.nekechs.shpee.checkers.core;

import java.util.List;

public abstract class MoveFactory {
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
     */

    public abstract Move generateMove(List<PositionVector> positionVectorList);
//    public abstract Move generateMove(PositionVector startingPosition, List<MoveVector> moveVectorList);
}
