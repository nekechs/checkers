package com.nekechs.shpee.checkers.core.vectors;

import java.util.Optional;

public class PositionVectorFactory extends VectorFactory{
    public Optional<BoardVector> generateVector(int row, int col) {
        return Optional.of(new PositionVector(row, col));
    }

    // Does not make sense for
    public Optional<BoardVector> generateVector(Direction direction, int magnitude) {
        return Optional.empty();
    }
}
