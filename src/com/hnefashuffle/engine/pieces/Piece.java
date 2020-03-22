package com.hnefashuffle.engine.pieces;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.board.Board;
import com.hnefashuffle.engine.board.Coordinates;
import com.hnefashuffle.engine.board.Move;
import javafx.util.Pair;

import java.util.Collection;

// TODO: piece interface instead of class
public abstract class Piece {

    protected Coordinates pieceCoordinates;
    protected Union pieceUnion;

    Piece(Coordinates pieceCoordinates, Union pieceUnion) {
        this.pieceCoordinates = pieceCoordinates;
        this.pieceUnion = pieceUnion;
    }

    public Union getPieceUnion() {
        return this.pieceUnion;
    }

    public Coordinates getPieceCoordinates() {
        return this.pieceCoordinates;
    }

    public abstract Collection<Move> calculateLegalMoves(Board board);

    public abstract boolean isCaptured();
}