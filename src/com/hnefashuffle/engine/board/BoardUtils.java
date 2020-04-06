package com.hnefashuffle.engine.board;

import java.util.Objects;

public class BoardUtils {

    public static int SIZE = 11;

    public static boolean isValidPath(Coordinates pieceCoordinates, Coordinates destinationCoordinates, Board board) {

        boolean equalX = pieceCoordinates.getXCoordinate() == destinationCoordinates.getXCoordinate();
        boolean equalY = pieceCoordinates.getYCoordinate() == destinationCoordinates.getYCoordinate();

        boolean isStraight = equalX ^ equalY;
        if (!isStraight) {
            return false;
        }

        boolean isClear = true;
        if (equalY) {
            if(pieceCoordinates.getXCoordinate() < destinationCoordinates.getXCoordinate()) {
                for(int i = pieceCoordinates.getXCoordinate() + 1; i < destinationCoordinates.getXCoordinate(); i++) {
                    if(Objects.requireNonNull(board.getTile(Coordinates.getCoordinates(i, destinationCoordinates.getYCoordinate()))).isOccupied()) {
                        isClear = false;
                        break;
                    }
                }
            } else {
                for(int i = pieceCoordinates.getXCoordinate() - 1; i > destinationCoordinates.getXCoordinate(); i--) {
                    if(Objects.requireNonNull(board.getTile(Coordinates.getCoordinates(i, destinationCoordinates.getYCoordinate()))).isOccupied()) {
                        isClear = false;
                        break;
                    }
                }
            }
        } else {
            if(pieceCoordinates.getYCoordinate() < destinationCoordinates.getYCoordinate()) {
                for(int i = pieceCoordinates.getYCoordinate() + 1; i < destinationCoordinates.getYCoordinate(); i++) {
                    if(Objects.requireNonNull(board.getTile(Coordinates.getCoordinates(destinationCoordinates.getXCoordinate(), i))).isOccupied()) {
                        isClear = false;
                        break;
                    }
                }
            } else {
                for(int i = pieceCoordinates.getYCoordinate() - 1; i > destinationCoordinates.getYCoordinate(); i--) {
                    if(Objects.requireNonNull(board.getTile(Coordinates.getCoordinates(destinationCoordinates.getXCoordinate(), i))).isOccupied()) {
                        isClear = false;
                        break;
                    }
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
