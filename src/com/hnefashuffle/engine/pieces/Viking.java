package com.hnefashuffle.engine.pieces;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.board.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Viking extends Piece {

    public Viking(Coordinates pieceCoordinates, Union pieceUnion) {
        super(pieceCoordinates, pieceUnion);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        List<Move> legalMoves = new ArrayList<>();

        for(int x = 0; x < BoardUtils.SIZE; x++){
            for(int y = 0; y < BoardUtils.SIZE; y++){
                Coordinates destinationCoordinates = new Coordinates(x, y);

                Tile destinationTile = Board.getTile(destinationCoordinates);

                assert destinationTile != null;
                if (!destinationTile.isOccupied() &&
                    destinationTile.getType().equals("default") &&
                    this.pieceCoordinates != destinationCoordinates &&
                    BoardUtils.isValidPath(this.pieceCoordinates, destinationCoordinates))
                {
                    legalMoves.add(new Move(board, this, destinationCoordinates));
                }
            }
        }
        return Collections.unmodifiableCollection(legalMoves);
    }

    @Override
    public boolean isCaptured() {
        return false;
    }
}