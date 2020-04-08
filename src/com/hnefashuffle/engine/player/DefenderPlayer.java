package com.hnefashuffle.engine.player;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.board.Board;
import com.hnefashuffle.engine.board.Move;
import com.hnefashuffle.engine.pieces.Piece;

import java.util.Collection;

public class DefenderPlayer extends Player {
    public DefenderPlayer(Board board, Collection<Move> legalMoves) {
        super(board, legalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getDefendersPieces();
    }

    @Override
    public Union getUnion() {
        return Union.DEFENDER;
    }

    @Override
    public Player getOpponent() {
        return board.getAttackersPlayer();
    }

    @Override
    public boolean won() {
        return board.getKing() != null && board.getKing().isOnCornerTile();
    }
}
