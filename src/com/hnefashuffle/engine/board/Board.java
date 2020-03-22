package com.hnefashuffle.engine.board;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.pieces.Piece;
import javafx.util.Pair;

import java.util.Map;

public class Board {

    private Board(Builder builder) {

    }

    public static final int SIZE = 11;

    public static Tile getTile(Coordinates tileCoordinates) {
        return null;
    }

    public int getSize() {
        return SIZE;
    }

    public static class Builder {
        Map<Coordinates, Piece> boardConfig;
        Union nextMoveMaker;

        public Builder() {

        }

        public Builder setPiece(Piece piece) {
            this.boardConfig.put(piece.getPieceCoordinates(), piece);
            return this;
        }

        public Builder setMoveMaker(Union nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }
}