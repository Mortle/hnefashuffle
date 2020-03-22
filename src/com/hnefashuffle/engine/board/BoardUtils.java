package com.hnefashuffle.engine.board;

import java.util.Objects;

public class BoardUtils {

    public static final int SIZE = 11;

    public static boolean isValidPath(Coordinates pieceCoordinates, Coordinates destinationCoordinates, Board board) {

        boolean equalX = pieceCoordinates.getXCoordinate() == destinationCoordinates.getXCoordinate();
        boolean equalY = pieceCoordinates.getYCoordinate() == destinationCoordinates.getYCoordinate();

        boolean isStraight = equalX ^ equalY;
        if (!isStraight) {
            return false;
        }

        boolean isClear = true;
        if (equalX) {
            for(int x = pieceCoordinates.getXCoordinate(); x < destinationCoordinates.getXCoordinate() + 1; x++) {
                if(Objects.requireNonNull(board.getTile(Coordinates.getCoordinates(x, pieceCoordinates.getYCoordinate()))).isOccupied()) {
                    isClear = false;
                    break;
                }
            }
        } else {
            for(int y = pieceCoordinates.getYCoordinate(); y < destinationCoordinates.getYCoordinate() + 1; y++) {
                if(Objects.requireNonNull(board.getTile(Coordinates.getCoordinates(pieceCoordinates.getXCoordinate(), y))).isOccupied()) {
                    isClear = false;
                    break;
                }
            }
        }

        return isClear;
    }

    public static boolean isKingLimitedPath(Coordinates pieceCoordinates, Coordinates destinationCoordinates, int limitation) {
        return Math.abs(pieceCoordinates.getXCoordinate() - destinationCoordinates.getXCoordinate()) <= limitation &&
                Math.abs(pieceCoordinates.getYCoordinate() - destinationCoordinates.getYCoordinate()) <= limitation;
    }
}
