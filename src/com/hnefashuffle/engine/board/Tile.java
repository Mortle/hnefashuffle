package com.hnefashuffle.engine.board;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.pieces.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    String tileType;
    Coordinates tileCoordinates;

    private static Map<Coordinates, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Coordinates, EmptyTile> createAllPossibleEmptyTiles() {

        Map<Coordinates, EmptyTile> emptyTileMap = new HashMap<>();

        for(int i = 0; i < BoardUtils.SIZE; i++) {
            for(int j = 0; j < BoardUtils.SIZE; j++) {
                Coordinates coordinates = Coordinates.getCoordinates(i, j);

                if (i == j && i == BoardUtils.SIZE / 2) {
                    emptyTileMap.put(coordinates, new EmptyTile(coordinates, "throne"));
                } else if (i == 0 || j == 0 || i == BoardUtils.SIZE - 1 || j == BoardUtils.SIZE - 1) {
                    emptyTileMap.put(coordinates, new EmptyTile(coordinates, "corner"));
                } else {
                    emptyTileMap.put(coordinates, new EmptyTile(coordinates, "default"));
                }
            }
        }

        return Collections.unmodifiableMap(emptyTileMap);
    }

    private Tile(Coordinates tileCoordinates, String tileType) {
        this.tileCoordinates = tileCoordinates;
        this.tileType = tileType;
    }

    public static Tile createTile(Coordinates tileCoordinates, Piece piece, String tileType) {
        return piece != null ? new OccupiedTile(tileCoordinates, tileType, piece) : EMPTY_TILES_CACHE.get(tileCoordinates);
    }

    public abstract boolean isOccupied();

    public abstract Piece getPiece();

    public String getType() { return tileType; }

    public static final class EmptyTile extends Tile {

        private EmptyTile(Coordinates tileCoordinates, String type) {
            super(tileCoordinates, type);
        }

        @Override
        public String toString() {
            return "-";
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
        public String toString() {
            return piece.getPieceUnion() == Union.ATTACKER ? piece.toString().toLowerCase() : piece.toString();
        }

        @Override
        public boolean isOccupied() { return true; }

        @Override
        public Piece getPiece() { return this.piece; }
    }
}