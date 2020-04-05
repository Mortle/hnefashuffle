package com.hnefashuffle.gui;

import com.hnefashuffle.engine.board.BoardUtils;
import com.hnefashuffle.engine.board.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private JFrame gameFrame;
    private BoardPanel boardPanel;

    private static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
    private static Dimension BOARD_PANEL_DIMENSION = new Dimension(440,440);
    private static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

    private static Color defaultTileColor = Color.decode("#FFB965");
    private static Color cornerTileColor = Color.decode("#925000");
    private static Color throneTileColor = Color.decode("#593E1A");
    private static Color tileBorderColor = Color.decode("#000000");

    public Table() {
        this.gameFrame = new JFrame("Hnefashuffle");
        this.gameFrame.setLayout(new BorderLayout());

        JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);

        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);

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
            validate();
        }

        private void assignTileColor() {
            setBorder(BorderFactory.createLineBorder(tileBorderColor));
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
