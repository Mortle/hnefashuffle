package com.hnefashuffle.engine;

import com.hnefashuffle.engine.board.Board;

public class Hnefashuffle {
    public static void main(String[] args) {
        Board board = Board.createInitialBoard();

        System.out.println(board);
    }
}
