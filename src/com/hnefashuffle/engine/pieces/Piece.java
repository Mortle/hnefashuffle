package com.hnefashuffle.engine.pieces;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.board.Board;
import com.hnefashuffle.engine.board.Move;
import javafx.util.Pair;

import java.util.Collection;

public abstract class Piece {

    protected Pair<Integer, Integer> pieceCoordinates;
    protected Union pieceUnion;

    Piece(Pair<Integer, Integer> pieceCoordinates, Union pieceUnion) {
        this.pieceCoordinates = pieceCoordinates;
        this.pieceUnion = pieceUnion;
    }

    public Union getPieceUnion() {
        return this.pieceUnion;
    }

    public abstract Collection<Move> calculateLegalMoves(Board board);

    public abstract boolean isCaptured();
}