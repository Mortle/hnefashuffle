package com.hnefashuffle.saveio;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.board.Board;
import com.hnefashuffle.engine.board.Coordinates;
import com.hnefashuffle.engine.pieces.King;
import com.hnefashuffle.engine.pieces.Piece;
import com.hnefashuffle.engine.pieces.Viking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SaveUtilities {

    private SaveUtilities() {
        throw new RuntimeException("Not Instantiable!");
    }

    public static Board loadBoard(File file) throws IOException, NoSuchElementException {
        Board.Builder builder = new Board.Builder();

        Scanner scanner = new Scanner(file);
        builder.setMoveMaker(Union.valueOf(scanner.nextLine().toUpperCase()));

        // Skip game history line
        scanner.nextLine();

        while(scanner.hasNext()){
            String[] tokens = scanner.nextLine().split(" ");

            int xCoordinate = Integer.parseInt(tokens[0]);
            int yCoordinate = Integer.parseInt(tokens[1]);
            String pieceString = tokens[2];
            String pieceUnion = tokens[3];

            if (pieceString.equals("V")) {
                builder.setPiece(new Viking(Coordinates.getCoordinates(xCoordinate, yCoordinate),
                        Union.valueOf(pieceUnion.toUpperCase())));
            } else if (pieceString.equals("K")) {
                builder.setPiece(new King(Coordinates.getCoordinates(xCoordinate, yCoordinate)));
            } else {
                throw new IOException("Wrong save file format!");
            }
        }
        scanner.close();
        return builder.build();
    }

    public static String[] loadGameHistory(File file) throws IOException, NoSuchElementException {
        Scanner scanner = new Scanner(file);
        scanner.nextLine();

        String[] tokens = scanner.nextLine().split(", ");

        scanner.close();
        return tokens;
    }

    public static void saveGame(Board board, String gameHistory, File file) throws IOException {
        FileWriter writer = new FileWriter(file);

        writer.write(board.getCurrentPlayer().getUnion().getOppositeUnion().toString());
        writer.write("\n");

        writer.write(gameHistory);
        writer.write("\n");

        for(Piece piece : board.getPieces()) {
            writer.write(String.format(
                                "%d %d %s %s \n",
                                piece.getPieceCoordinates().getXCoordinate(),
                                piece.getPieceCoordinates().getYCoordinate(),
                                piece.toString(),
                                piece.getPieceUnion().toString()
                            ));
        }
        writer.close();
    }
}
