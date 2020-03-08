package com.hnefashuffle.engine.board;

import com.hnefashuffle.engine.pieces.Piece;

/**
 * Created by Mortle on 03/08/20.
 */

public abstract class Tile {

    int x;
    int y;

    Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract boolean isOccupied();

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile {
        EmptyTile(int x, int y) {
            super(x, y);
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

        Piece piece;

        OccupiedTile(int x, int y, Piece piece) {
            super(x, y);
            this.piece = piece;
        }

        @Override
        public boolean isOccupied() { return true; }

        @Override
        public Piece getPiece() { return this.piece; }
    }
}