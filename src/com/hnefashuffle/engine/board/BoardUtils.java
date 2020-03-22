package com.hnefashuffle.engine.board;

import javafx.util.Pair;

import java.util.Objects;

public class BoardUtils {
    public static boolean isPathValid(Coordinates pieceCoordinates, Coordinates destinationCoordinates) {

        boolean equalX = pieceCoordinates.getXCoordinate() == destinationCoordinates.getXCoordinate();
        boolean equalY = pieceCoordinates.getYCoordinate() == destinationCoordinates.getYCoordinate();

        boolean isStraight = equalX ^ equalY;
        if (!isStraight) {
            return false;
        }

        boolean isClear = true;
        if (equalX) {
            for(int x = pieceCoordinates.getXCoordinate(); x < destinationCoordinates.getXCoordinate() + 1; x++) {
                if(Objects.requireNonNull(Board.getTile(new Coordinates(x, pieceCoordinates.getYCoordinate()))).isOccupied()) {
                    isClear = false;
                    break;
                }
            }
        } else {
            for(int y = pieceCoordinates.getYCoordinate(); y < destinationCoordinates.getYCoordinate() + 1; y++) {
                if(Objects.requireNonNull(Board.getTile(new Coordinates(pieceCoordinates.getXCoordinate(), y))).isOccupied()) {
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
