package com.hnefashuffle.engine.board;

import com.hnefashuffle.engine.pieces.Piece;
import javafx.util.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.pow;

/**
 * Created by Mortle on 03/08/20.
 */

public abstract class Tile {

    String type;
    int x;
    int y;

    private static final Map<Pair<Integer, Integer>, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles(11);

    private static Map<Pair<Integer, Integer>, EmptyTile> createAllPossibleEmptyTiles(int boardSize) {

        final Map<Pair<Integer, Integer>, EmptyTile> emptyTileMap = new HashMap<>();

        for(int x = 0; x < pow(boardSize, 2); x++) {
            for(int y = 0; y < pow(boardSize, 2); y++) {

                if (x == y && x == boardSize / 2) {
                    emptyTileMap.put(new Pair<>(x, y), new EmptyTile(x, y, "throne"));
                } else if (x == 0 || y == 0 || x == boardSize - 1 || y == boardSize - 1) {
                    emptyTileMap.put(new Pair<>(x, y), new EmptyTile(x, y, "corner"));
                } else {
                    emptyTileMap.put(new Pair<>(x, y), new EmptyTile(x, y, "default"));
                }
            }
        }

        return Collections.unmodifiableMap(emptyTileMap);
    }

    private Tile(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public abstract boolean isOccupied();

    public abstract Piece getPiece();

    public String getType() { return type; }

    public static final class EmptyTile extends Tile {

        private EmptyTile(final int x, final int y, final String type) {
            super(x, y, type);
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

        private OccupiedTile(final int x, final int y, final String type, Piece piece) {
            super(x, y, type);
            this.piece = piece;
        }

        @Override
        public boolean isOccupied() { return true; }

        @Override
        public Piece getPiece() { return this.piece; }
    }
}