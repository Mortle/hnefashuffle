package com.hnefashuffle.engine.board;

import java.util.ArrayList;
import java.util.List;

public class Coordinates {

    int xCoordinate;
    int yCoordinate;
    private static List<Coordinates> COORDINATES_CACHE = createAllPossibleCoordinates();

    public Coordinates(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    private static List<Coordinates> createAllPossibleCoordinates() {
        List<Coordinates> coordinatesList = new ArrayList<>();
        for(int i = 0; i < BoardUtils.SIZE; i++) {
            for(int j = 0; j < BoardUtils.SIZE; j++) {
                coordinatesList.add(new Coordinates(i, j));
            }
        }
        return coordinatesList;
    }

    public int getXCoordinate(){
        return this.xCoordinate;
    }

    public int getYCoordinate(){
        return this.yCoordinate;
    }

    public static Coordinates getCoordinates(int xCoordinate, int yCoordinate) {
        for(Coordinates coordinates : COORDINATES_CACHE) {
            if(coordinates.getXCoordinate() == xCoordinate && coordinates.getYCoordinate() == yCoordinate) {
                return coordinates;
            }
        }
        return null;
    }
}
