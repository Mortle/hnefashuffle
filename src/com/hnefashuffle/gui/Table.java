package com.hnefashuffle.gui;

import com.hnefashuffle.engine.board.*;
import com.hnefashuffle.engine.pieces.Piece;
import com.hnefashuffle.saveio.SaveUtilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {
    private JFrame gameFrame;
    private BoardPanel boardPanel;
    private GameHistoryPanel gameHistoryPanel;
    private Board gameBoard;

    private boolean gameEnded;
    private boolean highlightLegalMoves;

    private Tile sourceTile;
    private Tile destinationTile;
    private Piece playerMovedPiece;

    private static Dimension OUTER_FRAME_DIMENSION = new Dimension(910,750);
    private static Dimension BOARD_PANEL_DIMENSION = new Dimension(440,440);
    private static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

    private static String PIECE_ICON_PATH = "res/pieces/default/";

    private static Color defaultTileColor = Color.decode("#FFF1CE");
    private static Color cornerTileColor = Color.decode("#E6D4A8");
    private static Color throneTileColor = Color.decode("#E6D4A8");
    private static Color tileBorderColor = Color.decode("#000000");
    private static Color tileHighlightColor = Color.decode("#CCCCFF");

    public Table() {
        this.gameBoard = Board.createInitialBoard();
        this.gameFrame = new JFrame("Hnefashuffle");
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setResizable(false);

        this.highlightLegalMoves = true;
        this.gameEnded = false;

        JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);

        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        this.gameHistoryPanel = new GameHistoryPanel();
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);

        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(actionEvent -> {
            this.gameEnded = false;
            this.gameHistoryPanel.clear();
            this.gameBoard = Board.createInitialBoard();
            this.boardPanel.drawBoard(this.gameBoard);
        });
        fileMenu.add(newGame);

        JMenuItem loadGame = new JMenuItem("Load Game");
        loadGame.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int option = chooser.showOpenDialog(gameFrame);
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    this.gameBoard = SaveUtilities.loadBoard(chooser.getSelectedFile());
                    this.gameEnded = false;
                    this.gameHistoryPanel.clear();
                    this.gameHistoryPanel.load(SaveUtilities.loadGameHistory(chooser.getSelectedFile()));
                    this.boardPanel.drawBoard(this.gameBoard);
                }
                catch (IOException | NoSuchElementException | NullPointerException ex) {
                    JOptionPane.showMessageDialog(
                            this.gameFrame,
                            "Wrong save file format",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    ex.printStackTrace();
                }
            }
        });
        fileMenu.add(loadGame);

        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public String getDescription() {
                    return ".hnef";
                }
                @Override
                public boolean accept(File file) {
                    return file.isDirectory() || file.getName().toLowerCase().endsWith("hnef");
                }
            });
            final int option = chooser.showSaveDialog(this.gameFrame);
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    SaveUtilities.saveGame(
                            this.gameBoard,
                            this.gameHistoryPanel.toString(),
                            chooser.getSelectedFile());
                    showMessageDialog(null, "Successfully saved the game!");
                } catch (IOException | NullPointerException ex) {
                    JOptionPane.showMessageDialog(
                            this.gameFrame,
                            "Something went wrong",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    ex.printStackTrace();
                }
            }
        });
        fileMenu.add(saveGame);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(actionEvent -> System.exit(0));
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    private JMenu createPreferencesMenu() {
        JMenu preferencesMenu = new JMenu("Preferences");
        JCheckBoxMenuItem legalMoveHighlighterCheckbox = new JCheckBoxMenuItem("Highlight legal moves", true);
        legalMoveHighlighterCheckbox.addActionListener(actionEvent -> highlightLegalMoves = legalMoveHighlighterCheckbox.isSelected());
        preferencesMenu.add(legalMoveHighlighterCheckbox);
        return preferencesMenu;
    }

    private class BoardPanel extends JPanel {
        List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(BoardUtils.SIZE, BoardUtils.SIZE));
            this.boardTiles = new ArrayList<>();
            gameEnded = false;
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

        public void drawBoard(Board board) {
            if (!gameEnded) {
                removeAll();
                for (TilePanel tilePanel : boardTiles) {
                    tilePanel.drawTile(board);
                    add(tilePanel);
                }
                validate();
                repaint();
                if (board.getAttackersPlayer().won()) {
                    showMessageDialog(null, "Attackers won!");
                    gameEnded = true;
                } else if (board.getDefendersPlayer().won()) {
                    showMessageDialog(null, "Defenders won!");
                    gameEnded = true;
                }
            }
        }
    }

    private class TilePanel extends JPanel {
        private Coordinates tileCoordinates;
        private boolean highlightCandidateTile;

        TilePanel(BoardPanel boardPanel, Coordinates tileCoordinates) {
            super(new GridBagLayout());
            this.tileCoordinates = tileCoordinates;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            setBorder(BorderFactory.createLineBorder(tileBorderColor));
            assignTilePieceIcon(gameBoard);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    if(isRightMouseButton(mouseEvent) && !gameEnded) {
                        clearSelections();
                    } else if (isLeftMouseButton(mouseEvent) && !gameEnded) {
                        // Source tile selected
                        if (sourceTile == null) {
                            sourceTile = gameBoard.getTile(tileCoordinates);
                            playerMovedPiece = sourceTile.getPiece();
                            if (playerMovedPiece == null) {
                                sourceTile = null;
                            }
                        // Destination tile selected
                        } else {
                            destinationTile = gameBoard.getTile(tileCoordinates);
                            Move move = Move.createMove(gameBoard, sourceTile.getTileCoordinates(), destinationTile.getTileCoordinates());
                            MoveTransition transition = gameBoard.getCurrentPlayer().makeMove(move);
                            if (transition.getMoveStatus().isDone()) {
                                gameBoard = transition.getTransitionBoard();
                                gameHistoryPanel.update(move);
                            }
                            clearSelections();
                        }
                        SwingUtilities.invokeLater(() -> boardPanel.drawBoard(gameBoard));
                    }
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {}

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {}

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {
                    if (!gameEnded) {
                        highlightCandidateTile = true;
                        SwingUtilities.invokeLater(() -> drawTile(gameBoard));
                    }
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    if (!gameEnded) {
                        highlightCandidateTile = false;
                        SwingUtilities.invokeLater(() -> drawTile(gameBoard));
                    }
                }
            });

            validate();
        }

        private void clearSelections() {
            sourceTile = null;
            destinationTile = null;
            playerMovedPiece = null;
        }

        private void assignTilePieceIcon(Board board) {
            this.removeAll();
            if (board.getTile(this.tileCoordinates).isOccupied()) {
                try {
                    BufferedImage image =
                            ImageIO.read(new File(PIECE_ICON_PATH + board.getTile(this.tileCoordinates).getPiece().getPieceUnion().toString().substring(0, 1) +
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

        public void drawTile(Board board) {
            assignTileColor();
            setBorder(BorderFactory.createLineBorder(tileBorderColor));
            assignTilePieceIcon(board);
            highlightLegals(board);
            highlightTile(board);
            validate();
            repaint();
        }

        private void highlightTile(Board board) {
            if(highlightCandidateTile && board.getTile(tileCoordinates).isOccupied() &&
                    board.getTile(tileCoordinates).getPiece().getPieceUnion() == board.getCurrentPlayer().getUnion()) {
                setBackground(tileHighlightColor);
            }
        }

        private void highlightLegals(Board board) {
            if(highlightLegalMoves) {
                for(Move move : pieceLegalMoves(board)) {
                    if(move.getDestinationCoordinates() == this.tileCoordinates) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("res/tiles/highlight.png")))));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(Board board) {
            if (playerMovedPiece != null && playerMovedPiece.getPieceUnion() == board.getCurrentPlayer().getUnion()) {
                return playerMovedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }
    }
}
