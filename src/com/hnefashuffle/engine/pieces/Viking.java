package com.hnefashuffle.engine.pieces;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.board.*;

import java.util.*;

public class Viking extends Piece {

    public Viking(Coordinates pieceCoordinates, Union pieceUnion) {
        super(pieceCoordinates, pieceUnion);
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
                    destinationTile.getType().equals("default") &&
                    this.pieceCoordinates != destinationCoordinates &&
                    BoardUtils.isValidPath(this.pieceCoordinates, destinationCoordinates, board))
                {
                    legalMoves.add(new Move(board, this, destinationCoordinates));
                }
            }
        }
        return Collections.unmodifiableCollection(legalMoves);
    }

    @Override
    public boolean isCaptured(Board board) {
        int horizontalDangerCounter = 0;
        int verticalDangerCounter = 0;
        int xCoordinate = pieceCoordinates.getYCoordinate();
        int yCoordinate = pieceCoordinates.getXCoordinate();
        Map<String, Coordinates> candidateCoordinates = new HashMap<>();
        Map<String, Tile> candidateTiles = new HashMap<>();

        candidateCoordinates.put("upper", Coordinates.getCoordinates(xCoordinate, yCoordinate + 1));
        candidateCoordinates.put("right", Coordinates.getCoordinates(xCoordinate + 1, yCoordinate));
        candidateCoordinates.put("lower", Coordinates.getCoordinates(xCoordinate, yCoordinate - 1));
        candidateCoordinates.put("left", Coordinates.getCoordinates(xCoordinate - 1, yCoordinate));
        for(Map.Entry<String, Coordinates> entry : candidateCoordinates.entrySet()) {
            Coordinates coordinates = entry.getValue();
            if(coordinates != null) {
                candidateTiles.put(entry.getKey(), board.getTile(coordinates));
            }
        }
        for(Map.Entry<String, Tile> entry : candidateTiles.entrySet()) {
            Tile tile = entry.getValue();
            String direction = entry.getKey();
            if (tile.getType().equals("corner") || tile.getType().equals("throne")) {
                if (direction.equals("upper") || direction.equals("lower")) {
                    verticalDangerCounter++;
                } else {
                    horizontalDangerCounter++;
                }
            } else if (tile.getPiece() != null && tile.getPiece().getPieceUnion() == Union.ATTACKER) {
                if (direction.equals("right") || direction.equals("left")) {
                    horizontalDangerCounter++;
                } else {
                    verticalDangerCounter++;
                }
            }
        }
        return horizontalDangerCounter == 2 || verticalDangerCounter == 2;
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public Viking movePiece(Move move) {
        return new Viking(move.getDestinationCoordinates(), move.getMovedPiece().getPieceUnion());
    }

    @Override
    public String toString() {
        return "V";
    }

    @Override
    public String pieceType() {
        return "VIKING";
    }
}
