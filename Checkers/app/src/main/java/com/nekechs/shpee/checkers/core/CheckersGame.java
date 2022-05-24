package com.nekechs.shpee.checkers.core;

import java.util.List;
import java.util.LinkedList;
import java.util.Stack;

public class CheckersGame {
    Stack<CheckersBoard> boardList;

    public CheckersGame() {
        boardList = new Stack<CheckersBoard>();
        boardList.add(new CheckersBoard());
    }

    public void printBoardStdOut() {
        System.out.println(boardList.peek());
    }

    public String toString() {
        return boardList.peek().toString();
    }

    public void processRequestedMove(Move move) {

    }

}
