package com.hnefashuffle.engine.pieces;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.board.Board;
import com.hnefashuffle.engine.board.Coordinates;
import com.hnefashuffle.engine.board.Move;

import java.util.Collection;

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
    public abstract boolean isCaptured(Board board);
    public abstract boolean isKing();
    public abstract Piece movePiece(Move move);
    public abstract String pieceType();

    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }
        if(!(other instanceof Piece)) {
            return false;
        }
        Piece otherPiece = (Piece) other;
        return pieceCoordinates == otherPiece.getPieceCoordinates() && pieceUnion == otherPiece.getPieceUnion() &&
                this.pieceType().equals(otherPiece.pieceType());
    }
}