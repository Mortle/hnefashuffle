package com.hnefashuffle.engine.board;

import javafx.util.Pair;

public class Board {

    int boardSize;

    public Tile getTile(Pair<Integer, Integer> tileCoordinates) {
        return null;
    }

    public int getSize() {
        return this.boardSize;
    }

    public boolean isPathValid(Pair<Integer, Integer> pieceCoordinates, Pair<Integer, Integer> destinationCoordinates) {

        boolean equalX = pieceCoordinates.getKey().equals(destinationCoordinates.getKey());
        boolean equalY = pieceCoordinates.getValue().equals(destinationCoordinates.getValue());

        boolean isStraight = equalX ^ equalY;
        if (!isStraight) {
            return false;
        }

        boolean isClear = true;
        if (equalX) {
            for(int x = pieceCoordinates.getKey(); x < destinationCoordinates.getKey() + 1; x++) {
                if(this.getTile(new Pair<>(x, pieceCoordinates.getValue())).isOccupied()) {
                    isClear = false;
                    break;
                }
            }
        } else {
            for(int y = pieceCoordinates.getValue(); y < destinationCoordinates.getValue() + 1; y++) {
                if(this.getTile(new Pair<>(pieceCoordinates.getKey(), y)).isOccupied()) {
                    isClear = false;
                    break;
                }
            }
        }

        return isClear;
    }
}