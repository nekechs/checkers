package com.nekechs.shpee.checkers.core;

import java.util.Stack;
public class Game {
    Stack<Board> boards;
    enum GameState {
        DRAW,
        RESIGN,
        STALEMATE,
        CHECKMATE,
        ILLEGALMOVE,
        NORMALMOVE
    }

    final Team white = new Team('w', Board.WHITE_RIGHT_CORNER);
    final Team black = new Team('b', Board.BLACK_RIGHT_CORNER);

    public Game() {
        boards = new Stack<Board>();
        boards.push(new Board(this));

    }

    public void processMoveRequest(CaptureMove move) {
        
    }

    public String toString() {
        return boards.peek().toString();
    }
}
