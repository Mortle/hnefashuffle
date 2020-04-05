package com.hnefashuffle;

import com.hnefashuffle.engine.board.Board;
import com.hnefashuffle.gui.Table;

public class Hnefashuffle {
    public static void main(String[] args) {
        Board board = Board.createInitialBoard();

        System.out.println(board);

        Table table = new Table();
    }
}
