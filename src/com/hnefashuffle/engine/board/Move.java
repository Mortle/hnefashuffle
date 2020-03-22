package com.hnefashuffle.engine.board;

import com.hnefashuffle.engine.pieces.Piece;

public class Move {

    Board board;
    Piece movedPiece;
    Coordinates destinationCoordinates;

    public Move(Board board, Piece movedPiece, Coordinates destinationCoordinates) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinates = destinationCoordinates;
    }

    public Board execute() {
        return null;
    }
}