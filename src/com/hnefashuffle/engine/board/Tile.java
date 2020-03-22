package com.hnefashuffle.engine.board;

import com.hnefashuffle.engine.pieces.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    String tileType;
    Coordinates tileCoordinates;

    private static final Map<Coordinates, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Coordinates, EmptyTile> createAllPossibleEmptyTiles() {

        final Map<Coordinates, EmptyTile> emptyTileMap = new HashMap<>();

        for(int x = 0; x < Board.SIZE; x++) {
            for(int y = 0; y < Board.SIZE; y++) {

                if (x == y && x == Board.SIZE / 2) {
                    emptyTileMap.put(new Coordinates(x, y), new EmptyTile(new Coordinates(x, y), "throne"));
                } else if (x == 0 || y == 0 || x == Board.SIZE - 1 || y == Board.SIZE - 1) {
                    emptyTileMap.put(new Coordinates(x, y), new EmptyTile(new Coordinates(x, y), "corner"));
                } else {
                    emptyTileMap.put(new Coordinates(x, y), new EmptyTile(new Coordinates(x, y), "default"));
                }
            }
        }

        return Collections.unmodifiableMap(emptyTileMap);
    }

    private Tile(Coordinates tileCoordinates, String tileType) {
        this.tileCoordinates = tileCoordinates;
        this.tileType = tileType;
    }

    public abstract boolean isOccupied();

    public abstract Piece getPiece();

    public String getType() { return tileType; }

    public static final class EmptyTile extends Tile {

        private EmptyTile(Coordinates tileCoordinates, String type) {
            super(tileCoordinates, type);
        }

        @Override
        public boolean isOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static final class OccupiedTile extends Tile {

        private Piece piece;

        private OccupiedTile(Coordinates tileCoordinates, final String type, Piece piece) {
            super(tileCoordinates, type);
            this.piece = piece;
        }

        @Override
        public boolean isOccupied() { return true; }

        @Override
        public Piece getPiece() { return this.piece; }
    }
}