package com.hnefashuffle.gui;

import com.hnefashuffle.engine.board.Board;
import com.hnefashuffle.engine.board.BoardUtils;
import com.hnefashuffle.engine.board.Coordinates;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private JFrame gameFrame;
    private BoardPanel boardPanel;
    private Board gameBoard;

    private static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
    private static Dimension BOARD_PANEL_DIMENSION = new Dimension(440,440);
    private static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

    private static String pieceIconPath = "res/pieces/default/";

    private static Color defaultTileColor = Color.decode("#FFB965");
    private static Color cornerTileColor = Color.decode("#925000");
    private static Color throneTileColor = Color.decode("#925000");
    private static Color tileBorderColor = Color.decode("#000000");

    public Table() {
        this.gameBoard = Board.createInitialBoard();
        this.gameFrame = new JFrame("Hnefashuffle");
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);

        JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);

        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem openPGNmenuItem = new JMenuItem("Load PGN File");
        openPGNmenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Open Up that PGN File!");
            }
        });
        fileMenu.add(openPGNmenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    private class BoardPanel extends JPanel {
        List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(BoardUtils.SIZE, BoardUtils.SIZE));
            this.boardTiles = new ArrayList<>();
            for(int i = 0; i < BoardUtils.SIZE; i++) {
                for(int j = 0; j < BoardUtils.SIZE; j++) {
                    TilePanel tilePanel = new TilePanel(this, Coordinates.getCoordinates(i, j));
                    this.boardTiles.add(tilePanel);
                    add(tilePanel);
                }
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }
    }

    private class TilePanel extends JPanel {
        private Coordinates tileCoordinates;

        TilePanel(BoardPanel boardPanel, Coordinates tileCoordinates) {
            super(new GridBagLayout());
            this.tileCoordinates = tileCoordinates;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            setBorder(BorderFactory.createLineBorder(tileBorderColor));
            assignTilePieceIcon(gameBoard);
            validate();
        }

        private void assignTilePieceIcon(Board board) {
            this.removeAll();
            if (board.getTile(this.tileCoordinates).isOccupied()) {
                try {
                    BufferedImage image =
                            ImageIO.read(new File(pieceIconPath + board.getTile(this.tileCoordinates).getPiece().getPieceUnion().toString().substring(0, 1) +
                                    board.getTile(this.tileCoordinates).getPiece().toString() + ".png"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void assignTileColor() {
            if (Coordinates.getCornersCoordinates().contains(this.tileCoordinates)) {
                setBackground(cornerTileColor);
            } else if (this.tileCoordinates.getXCoordinate() == 5 && this.tileCoordinates.getYCoordinate() == 5) {
                setBackground(throneTileColor);
            } else {
                setBackground(defaultTileColor);
            }
        }
    }
}
