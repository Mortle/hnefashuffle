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

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public Coordinates getDestinationCoordinates() {
        return this.destinationCoordinates;
    }

    public Board execute() {
        Board.Builder builder = new Board.Builder();

        for(Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if(!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for(Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        // Move the movedPiece
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getUnion());

        return builder.build();
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }
        if(!(other instanceof Move)) {
            return false;
        }
        Move otherMove = (Move) other;
        return getDestinationCoordinates() == otherMove.getDestinationCoordinates() &&
                getMovedPiece().equals(otherMove.getMovedPiece());
    }
}