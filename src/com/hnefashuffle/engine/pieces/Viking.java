package com.hnefashuffle.engine.pieces;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.board.Board;
import com.hnefashuffle.engine.board.Move;
import com.hnefashuffle.engine.board.Tile;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Viking extends Piece {

    Viking(Pair<Integer, Integer> pieceCoordinates, Union pieceUnion) {
        super(pieceCoordinates, pieceUnion);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        List<Move> legalMoves = new ArrayList<>();

        for(int x = 0; x < board.getSize(); x++){
            for(int y = 0; y < board.getSize(); y++){
                Pair<Integer, Integer> destinationCoordinates = new Pair<Integer, Integer>(x, y);

                Tile destinationTile = board.getTile(destinationCoordinates);

                if (!destinationTile.isOccupied() &&
                    destinationTile.getType().equals("default") &&
                    this.pieceCoordinates != destinationCoordinates &&
                    board.isPathValid(this.pieceCoordinates, destinationCoordinates))
                {
                    legalMoves.add(new Move());
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