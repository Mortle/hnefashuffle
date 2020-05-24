package com.hnefashuffle.engine.board;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.pieces.King;
import com.hnefashuffle.engine.pieces.Piece;
import com.hnefashuffle.engine.pieces.Viking;
import com.hnefashuffle.engine.player.AttackerPlayer;
import com.hnefashuffle.engine.player.DefenderPlayer;
import com.hnefashuffle.engine.player.Player;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {

    private Map<Coordinates, Tile> gameBoard;
    private Collection<Piece> attackersPieces;
    private Collection<Piece> defendersPieces;
    private AttackerPlayer attackersPlayer;
    private DefenderPlayer defendersPlayer;
    private Player currentPlayer;

    private Board(Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.attackersPieces = calculateActivePieces(this.gameBoard, Union.ATTACKER);
        this.defendersPieces = calculateActivePieces(this.gameBoard, Union.DEFENDER);
        Collection<Move> attackersLegalMoves = calculateLegalMoves(this.attackersPieces);
        Collection<Move> defendersLegalMoves = calculateLegalMoves(this.defendersPieces);
        this.attackersPlayer = new AttackerPlayer(this, attackersLegalMoves);
        this.defendersPlayer = new DefenderPlayer(this, defendersLegalMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.attackersPlayer, this.defendersPlayer);
    }

    public Player getAttackersPlayer() {
        return attackersPlayer;
    }
    public Player getDefendersPlayer() {
        return defendersPlayer;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public Collection<Piece> getAttackersPieces() {
        return this.attackersPieces;
    }
    public Collection<Piece> getDefendersPieces() {
        return this.defendersPieces;
    }
    public King getKing() {
        for(Piece piece : defendersPieces) {
            if(piece.isKing()) {
                return (King) piece;
            }
        }
        return null;
    }

    private Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
        List<Move> legalMoves = new ArrayList<>();
        for(Piece piece : pieces) {
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return legalMoves;
    }

    private static Collection<Piece> calculateActivePieces(Map<Coordinates, Tile> gameBoard, Union union) {
        List<Piece> activePieces = new ArrayList<>();
        for(Map.Entry<Coordinates, Tile> entry : gameBoard.entrySet()) {
            Tile tile = entry.getValue();
            if(tile.isOccupied()) {
                Piece piece = tile.getPiece();
                if(piece.getPieceUnion() == union){
                    activePieces.add(piece);
                }
            }
        }
        return activePieces;
    }

    public Collection<Move> getAllLegalMoves() {
        return Stream.concat(this.defendersPlayer.getLegalMoves().stream(),
                this.attackersPlayer.getLegalMoves().stream()).collect(Collectors.toList());
    }

    public Collection<Piece> getPieces() {
        return Stream.concat(this.getDefendersPieces().stream(),
                this.getAttackersPieces().stream()).collect(Collectors.toList());
    }

    public Tile getTile(Coordinates tileCoordinates) {
        return gameBoard.get(tileCoordinates);
    }

    private static Map<Coordinates, Tile> createGameBoard(Builder builder) {
        Map<Coordinates, Tile> tiles = new HashMap<>();

        for(int i = 0; i < BoardUtils.SIZE; i++) {
            for(int j = 0; j < BoardUtils.SIZE; j++) {
                Coordinates tileCoordinates = Coordinates.getCoordinates(i, j);

                if (i == j && i == BoardUtils.SIZE / 2) {
                    tiles.put(tileCoordinates, Tile.createTile(tileCoordinates, builder.boardConfig.get(tileCoordinates), "throne"));
                } else if ((i == 0 && j == 0) ||
                        (i == 0 && j == BoardUtils.SIZE - 1) ||
                        (i == BoardUtils.SIZE - 1 && j == 0) ||
                        (i == BoardUtils.SIZE - 1 && j == BoardUtils.SIZE - 1)) {
                    tiles.put(tileCoordinates, Tile.createTile(tileCoordinates, builder.boardConfig.get(tileCoordinates), "corner"));
                } else {
                    tiles.put(tileCoordinates, Tile.createTile(tileCoordinates, builder.boardConfig.get(tileCoordinates), "default"));
                }
            }
        }

        return tiles;
    }

    // TODO: store standart initial coordinates in BoardUtils
    public static Board createInitialBoard() {
        Builder builder = new Builder();
        // Defenders
        builder.setPiece(new King(Coordinates.getCoordinates(BoardUtils.SIZE / 2, BoardUtils.SIZE / 2)));
        builder.setPiece(new Viking(Coordinates.getCoordinates(3, 5), Union.DEFENDER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(4, 5), Union.DEFENDER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(6, 5), Union.DEFENDER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(7, 5), Union.DEFENDER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(5, 7), Union.DEFENDER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(4, 6), Union.DEFENDER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(5, 6), Union.DEFENDER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(6, 6), Union.DEFENDER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(4, 4), Union.DEFENDER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(5, 4), Union.DEFENDER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(6, 4), Union.DEFENDER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(5, 3), Union.DEFENDER));

        // Attackers
        builder.setPiece(new Viking(Coordinates.getCoordinates(3, 0), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(4, 0), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(5, 0), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(5, 1), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(6, 0), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(7, 0), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(3, 10), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(4, 10), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(5, 10), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(5, 9), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(6, 10), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(7, 10), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(0, 3), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(0, 4), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(0, 5), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(0, 6), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(0, 7), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(1, 5), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(10, 3), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(10, 4), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(10, 5), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(9, 5), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(10, 6), Union.ATTACKER));
        builder.setPiece(new Viking(Coordinates.getCoordinates(10, 7), Union.ATTACKER));

        builder.setMoveMaker(Union.DEFENDER);
        return builder.build();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < BoardUtils.SIZE; i++) {
            for(int j = BoardUtils.SIZE - 1; j >= 0; j--) {
                Coordinates tileCoordinates = Coordinates.getCoordinates(i, j);

                String tileText = this.gameBoard.get(tileCoordinates).toString();
                builder.append(String.format("%3s", tileText));

                if(j == 0) {
                    builder.append("\n");
                }
            }
        }
        return builder.toString();
    }

    public static class Builder {
        Map<Coordinates, Piece> boardConfig;
        Union nextMoveMaker;

        public Builder() {
            this.boardConfig = new HashMap<>();
        }

        public Builder setPiece(Piece piece) {
            this.boardConfig.put(piece.getPieceCoordinates(), piece);
            return this;
        }

        public void setMoveMaker(Union nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
        }

        public Board build() {
            return new Board(this);
        }
    }
}