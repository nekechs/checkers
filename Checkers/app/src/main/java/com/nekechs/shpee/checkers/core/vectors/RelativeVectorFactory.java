package com.nekechs.shpee.checkers.core.vectors;

import java.util.Optional;

public class RelativeVectorFactory extends VectorFactory{
    public Optional<BoardVector> generateVector(int row, int col) {
        return Optional.of(new RelativeVector(row, col));
    }
    public Optional<BoardVector> generateVector(Direction direction, int magnitude) {
        switch(direction) {
            case NORTH:
                return Optional.of(new RelativeVector(-magnitude,0));
            case NORTHEAST:
                return Optional.of(new RelativeVector(-magnitude,magnitude));
            case EAST:
                return Optional.of(new RelativeVector(0,magnitude));
            case SOUTHEAST:
                return Optional.of(new RelativeVector(magnitude,magnitude));
            case SOUTH:
                return Optional.of(new RelativeVector(magnitude,0));
            case SOUTHWEST:
                return Optional.of(new RelativeVector(magnitude,-magnitude));
            case WEST:
                return Optional.of(new RelativeVector(0,-magnitude));
            case NORTHWEST:
                return Optional.of(new RelativeVector(-magnitude,-magnitude));
            default:
                return Optional.empty();
        }
    }
}
