package com.hnefashuffle.engine.board;

import com.hnefashuffle.engine.pieces.Piece;
import javafx.util.Pair;

public class Move {

    Board board;
    Piece movedPiece;
    Coordinates destinationCoordinates;

    public Move(Board board, Piece movedPiece, Coordinates destinationCoordinates) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinates = destinationCoordinates;
    }
}