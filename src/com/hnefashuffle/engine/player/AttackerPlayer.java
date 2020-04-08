package com.hnefashuffle.engine.player;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.board.Board;
import com.hnefashuffle.engine.board.Move;
import com.hnefashuffle.engine.pieces.Piece;

import java.util.Collection;

public class AttackerPlayer extends Player {
    public AttackerPlayer(Board board, Collection<Move> legalMoves) {
        super(board, legalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getAttackersPieces();
    }

    @Override
    public Union getUnion() {
        return Union.ATTACKER;
    }

    @Override
    public Player getOpponent() {
        return board.getDefendersPlayer();
    }

    @Override
    public boolean won() {
        return board.getKing() == null;
    }
}
