package com.hnefashuffle.engine.pieces;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.board.Board;
import com.hnefashuffle.engine.board.Move;
import javafx.util.Pair;

import java.util.List;

public abstract class Piece {

    protected Pair<Integer, Integer> piecePosition;
    protected Union pieceUnion;

    Piece(Pair<Integer, Integer> piecePosition, Union pieceUnion) {
        this.piecePosition = piecePosition;
        this.pieceUnion = pieceUnion;
    }

    public abstract List<Move> calculateLegalMoves(Board board);

}
