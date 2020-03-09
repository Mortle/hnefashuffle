package com.hnefashuffle.engine.board;

import com.hnefashuffle.engine.pieces.Piece;
import javafx.util.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    String type;
    Pair<Integer, Integer> coordinates;

    private static final Map<Pair<Integer, Integer>, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles(11);

    private static Map<Pair<Integer, Integer>, EmptyTile> createAllPossibleEmptyTiles(int boardSize) {

        final Map<Pair<Integer, Integer>, EmptyTile> emptyTileMap = new HashMap<>();

        for(int x = 0; x < boardSize; x++) {
            for(int y = 0; y < boardSize; y++) {

                if (x == y && x == boardSize / 2) {
                    emptyTileMap.put(new Pair<>(x, y), new EmptyTile(new Pair<>(x, y), "throne"));
                } else if (x == 0 || y == 0 || x == boardSize - 1 || y == boardSize - 1) {
                    emptyTileMap.put(new Pair<>(x, y), new EmptyTile(new Pair<>(x, y), "corner"));
                } else {
                    emptyTileMap.put(new Pair<>(x, y), new EmptyTile(new Pair<>(x, y), "default"));
                }
            }
        }

        return Collections.unmodifiableMap(emptyTileMap);
    }

    private Tile(Pair<Integer, Integer> coordinates, String type) {
        this.coordinates = coordinates;
        this.type = type;
    }

    public abstract boolean isOccupied();

    public abstract Piece getPiece();

    public String getType() { return type; }

    public static final class EmptyTile extends Tile {

        private EmptyTile(Pair<Integer, Integer> coordinates, String type) {
            super(coordinates, type);
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

        private OccupiedTile(Pair<Integer, Integer> coordinates, final String type, Piece piece) {
            super(coordinates, type);
            this.piece = piece;
        }

        @Override
        public boolean isOccupied() { return true; }

        @Override
        public Piece getPiece() { return this.piece; }
    }
}