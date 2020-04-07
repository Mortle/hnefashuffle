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

    public Coordinates getCurrentCoordinate() {
        return this.movedPiece.getPieceCoordinates();
    }

    public Coordinates getDestinationCoordinates() {
        return this.destinationCoordinates;
    }

    public static Move createMove(Board board,
                                  Coordinates currentCoordinates,
                                  Coordinates destinationCoordinates) {
        for(Move move : board.getAllLegalMoves()) {
            if (move.getCurrentCoordinate() == currentCoordinates &&
                    move.getDestinationCoordinates() == destinationCoordinates) {
                return move;
            }
        }
        return null;
    }

    public Board execute() {
        Board.Builder builder = new Board.Builder();

        for(Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for(Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        // Move the movedPiece
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.getCurrentPlayer().getUnion());

        // Remove captured pieces from board
        Board pendingBoard = builder.build();
        builder.boardConfig.entrySet().removeIf(e -> e.getValue().isCaptured(pendingBoard));

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