package com.hnefashuffle.engine.pieces;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.board.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class King extends Piece {

    private static final int STEP_LIMITATION = 3;

    public King(Coordinates pieceCoordinates) {
        super(pieceCoordinates, Union.DEFENDER);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        List<Move> legalMoves = new ArrayList<>();

        for(int x = 0; x < BoardUtils.SIZE; x++){
            for(int y = 0; y < BoardUtils.SIZE; y++){
                Coordinates destinationCoordinates = Coordinates.getCoordinates(x, y);

                Tile destinationTile = board.getTile(destinationCoordinates);

                assert destinationTile != null;
                assert destinationCoordinates != null;
                if (!destinationTile.isOccupied() &&
                    this.pieceCoordinates != destinationCoordinates &&
                    BoardUtils.isValidPath(this.pieceCoordinates, destinationCoordinates, board) &&
                    BoardUtils.isKingLimitedPath(this.pieceCoordinates, destinationCoordinates, STEP_LIMITATION))
                {
                    legalMoves.add(new Move(board, this, destinationCoordinates));
                }
            }
        }
        return Collections.unmodifiableCollection(legalMoves);
    }

    public boolean isOnCornerTile() {
        return Coordinates.getCornersCoordinates().contains(pieceCoordinates);
    }

    @Override
    public boolean isCaptured(Board board) {
        int surroundersCounter = 0;
        int xCoordinate = pieceCoordinates.getYCoordinate();
        int yCoordinate = pieceCoordinates.getXCoordinate();

        List<Coordinates> candidateCoordinates = new ArrayList<>();
        candidateCoordinates.add(Coordinates.getCoordinates(xCoordinate, yCoordinate + 1));
        candidateCoordinates.add(Coordinates.getCoordinates(xCoordinate + 1, yCoordinate));
        candidateCoordinates.add(Coordinates.getCoordinates(xCoordinate, yCoordinate - 1));
        candidateCoordinates.add(Coordinates.getCoordinates(xCoordinate - 1, yCoordinate));

        List<Tile> candidateTiles = new ArrayList<>();
        for(Coordinates coordinates : candidateCoordinates) {
            if(coordinates != null) {
                candidateTiles.add(board.getTile(coordinates));
            }
        }

        for(Tile tile : candidateTiles) {
            if (tile.getType().equals("corner") || tile.getType().equals("throne")) {
                surroundersCounter++;
            } else if (tile.getPiece() != null && tile.getPiece().getPieceUnion() == Union.ATTACKER) {
                surroundersCounter++;
            }
        }

        return surroundersCounter == 4;
    }

    @Override
    public boolean isKing() {
        return true;
    }

    @Override
    public String toString() {
        return "K";
    }
}
