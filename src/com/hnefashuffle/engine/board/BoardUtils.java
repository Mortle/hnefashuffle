package com.hnefashuffle.engine.board;

import javafx.util.Pair;

import java.util.Objects;

public class BoardUtils {
    public static boolean isPathValid(Pair<Integer, Integer> pieceCoordinates, Pair<Integer, Integer> destinationCoordinates) {

        boolean equalX = pieceCoordinates.getKey().equals(destinationCoordinates.getKey());
        boolean equalY = pieceCoordinates.getValue().equals(destinationCoordinates.getValue());

        boolean isStraight = equalX ^ equalY;
        if (!isStraight) {
            return false;
        }

        boolean isClear = true;
        if (equalX) {
            for(int x = pieceCoordinates.getKey(); x < destinationCoordinates.getKey() + 1; x++) {
                if(Objects.requireNonNull(Board.getTile(new Pair<>(x, pieceCoordinates.getValue()))).isOccupied()) {
                    isClear = false;
                    break;
                }
            }
        } else {
            for(int y = pieceCoordinates.getValue(); y < destinationCoordinates.getValue() + 1; y++) {
                if(Objects.requireNonNull(Board.getTile(new Pair<>(pieceCoordinates.getKey(), y))).isOccupied()) {
                    isClear = false;
                    break;
                }
            }
        }

        return isClear;
    }

    public static boolean isKingLimitedPath(Pair<Integer, Integer> pieceCoordinates, Pair<Integer, Integer> destinationCoordinates, int limitation) {
        return Math.abs(pieceCoordinates.getKey() - destinationCoordinates.getKey()) <= limitation &&
                Math.abs(pieceCoordinates.getValue() - destinationCoordinates.getValue()) <= limitation;
    }
}
