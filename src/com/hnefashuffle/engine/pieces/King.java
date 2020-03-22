package com.hnefashuffle.engine.pieces;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.board.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class King extends Piece {

    private static final int STEP_LIMITATION = 3;

    King(Coordinates pieceCoordinates) {
        super(pieceCoordinates, Union.DEFENDER);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        List<Move> legalMoves = new ArrayList<>();

        for(int x = 0; x < board.getSize(); x++){
            for(int y = 0; y < board.getSize(); y++){
                Coordinates destinationCoordinates = new Coordinates(x, y);

                Tile destinationTile = Board.getTile(destinationCoordinates);

                assert destinationTile != null;
                if (!destinationTile.isOccupied() &&
                    this.pieceCoordinates != destinationCoordinates &&
                    BoardUtils.isPathValid(this.pieceCoordinates, destinationCoordinates) &&
                    BoardUtils.isKingLimitedPath(this.pieceCoordinates, destinationCoordinates, STEP_LIMITATION))
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
