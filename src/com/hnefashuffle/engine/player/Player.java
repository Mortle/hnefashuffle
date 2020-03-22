package com.hnefashuffle.engine.player;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.board.Board;
import com.hnefashuffle.engine.board.Move;
import com.hnefashuffle.engine.board.MoveTransition;
import com.hnefashuffle.engine.pieces.Piece;

import java.util.Collection;

public abstract class Player {
    protected Board board;
    protected Collection<Move> legalMoves;

    Player(Board board, Collection<Move> legalMoves) {
        this.board = board;
        this.legalMoves = legalMoves;
    }

    public boolean isMoveLegal(Move move) {
        return this.legalMoves.contains(move);
    }

    public MoveTransition makeMove(Move move) {
        return null;
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Union getUnion();
    public abstract Player getOpponent();
    public abstract boolean won();
}
