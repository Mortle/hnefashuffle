package com.hnefashuffle.engine.board;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.pieces.King;
import com.hnefashuffle.engine.pieces.Piece;
import com.hnefashuffle.engine.pieces.Viking;

import java.util.*;

public class Board {

    private Map<Coordinates, Tile> gameBoard;

    private Board(Builder builder) {
        this.gameBoard = createGameBoard(builder);
    }

    public Tile getTile(Coordinates tileCoordinates) {
        return gameBoard.get(tileCoordinates);
    }

    private static Map<Coordinates, Tile> createGameBoard(Builder builder) {
        Map<Coordinates, Tile> tiles = new HashMap<>();

        for(int i = 0; i < BoardUtils.SIZE; i++) {
            for(int j = 0; j < BoardUtils.SIZE; j++) {
                Coordinates tileCoordinates = new Coordinates(i, j);

                if (i == j && i == BoardUtils.SIZE / 2) {
                    tiles.put(tileCoordinates, Tile.createTile(tileCoordinates, builder.boardConfig.get(tileCoordinates), "throne"));
                } else if (i == 0 || j == 0 || i == BoardUtils.SIZE - 1 || j == BoardUtils.SIZE - 1) {
                    tiles.put(tileCoordinates, Tile.createTile(tileCoordinates, builder.boardConfig.get(tileCoordinates), "corner"));
                } else {
                    tiles.put(tileCoordinates, Tile.createTile(tileCoordinates, builder.boardConfig.get(tileCoordinates), "default"));
                }
            }
        }

        return tiles;
    }

    // TODO: store standart initial coordinates in BoardUtils
    private static Board createInitialBoard() {
        Builder builder = new Builder();
        // Defenders
        builder.setPiece(new King(new Coordinates(BoardUtils.SIZE / 2, BoardUtils.SIZE / 2)));
        builder.setPiece(new Viking(new Coordinates(3, 5), Union.DEFENDER));
        builder.setPiece(new Viking(new Coordinates(4, 5), Union.DEFENDER));
        builder.setPiece(new Viking(new Coordinates(6, 5), Union.DEFENDER));
        builder.setPiece(new Viking(new Coordinates(7, 5), Union.DEFENDER));
        builder.setPiece(new Viking(new Coordinates(5, 7), Union.DEFENDER));
        builder.setPiece(new Viking(new Coordinates(4, 6), Union.DEFENDER));
        builder.setPiece(new Viking(new Coordinates(5, 6), Union.DEFENDER));
        builder.setPiece(new Viking(new Coordinates(6, 6), Union.DEFENDER));
        builder.setPiece(new Viking(new Coordinates(4, 4), Union.DEFENDER));
        builder.setPiece(new Viking(new Coordinates(5, 4), Union.DEFENDER));
        builder.setPiece(new Viking(new Coordinates(6, 4), Union.DEFENDER));

        // Attackers
        builder.setPiece(new Viking(new Coordinates(3, 0), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(4, 0), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(5, 0), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(5, 1), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(6, 0), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(7, 0), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(3, 10), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(4, 10), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(5, 10), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(5, 9), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(6, 10), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(7, 10), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(0, 3), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(0, 4), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(0, 5), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(0, 6), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(0, 7), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(1, 5), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(10, 3), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(10, 4), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(10, 5), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(9, 5), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(10, 6), Union.ATTACKER));
        builder.setPiece(new Viking(new Coordinates(10, 7), Union.ATTACKER));

        builder.setMoveMaker(Union.ATTACKER);
        return builder.build();
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