package com.nekechs.shpee.checkers.core;

public class CheckersGame {
    CheckersBoard board;

    public CheckersGame() {
        board = new CheckersBoard();
    }

    public void printBoardStdOut() {
        System.out.println(board);
    }

    public String toString() {
        return board.toString();
    }

}
