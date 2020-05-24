package com.hnefashuffle.gui;

import com.hnefashuffle.engine.Union;
import com.hnefashuffle.engine.board.Move;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GameHistoryPanel extends JPanel {

    private DataModel model;
    private JScrollPane scrollPane;
    private static Dimension HISTORY_PANEL_DIMENSION = new Dimension(150, 150);

    public GameHistoryPanel() {
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        JTable table = new JTable(model);
        table.setRowHeight(15);
        this.scrollPane = new JScrollPane(table);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setPreferredSize(HISTORY_PANEL_DIMENSION);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void clear() {
        this.model.clear();
    }

    public void update(Move move) {
        int col;
        if(move.getMovedPiece().getPieceUnion().equals(Union.DEFENDER)) {
            col = 1;
        } else {
            col = 0;
        }
        this.model.setNextValue(col, move.toString());
    }

    private static class Row {

        private String attackerMove;
        private String defenderMove;

        public String getAttackerMove() {
            return this.attackerMove;
        }

        public String getDefenderMove() {
            return this.defenderMove;
        }

        public void setAttackerMove(String move) {
            this.attackerMove = move;
        }

        public void setDefenderMove(String move) {
            this.defenderMove = move;
        }
    }

    private static class DataModel extends DefaultTableModel {

        private ArrayList<Row> values;
        private static String[] NAMES = { "Attackers", "Defenders" };

        DataModel() {
            this.values = new ArrayList<>();
        }

        public void clear() {
            this.values.clear();
            fireTableDataChanged();
            setRowCount(0);
        }

        public void setNextValue(int col, String move) {
            if (col == 0) {
                this.values.add(new Row());
                this.values.get(getRowCount() - 1).setAttackerMove(move);
                fireTableRowsInserted(getRowCount() - 1, col);
            } else if (col == 1) {
                if(getRowCount() == 0) {
                    this.values.add(new Row());
                }
                this.values.get(getRowCount() - 1).setDefenderMove(move);
                fireTableRowsInserted(getRowCount() - 1, col);
            }
        }

        @Override
        public int getRowCount() {
            if(this.values == null) {
                return 0;
            }
            return this.values.size();
        }

        @Override
        public int getColumnCount() {
            return NAMES.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            Row currentRow = this.values.get(row);
            if (col == 0) {
                return currentRow.getAttackerMove();
            } else if (col == 1) {
                return currentRow.getDefenderMove();
            }
            return null;
        }

        @Override
        public void setValueAt(Object aValue,
                               int row,
                               int col) {
            Row currentRow;
            if(this.values.size() <= row) {
                currentRow = new Row();
                this.values.add(currentRow);
            } else {
                currentRow = this.values.get(row);
            }
            if(col == 0) {
                currentRow.setAttackerMove((String) aValue);
                fireTableRowsInserted(row, row);
            } else if(col == 1) {
                currentRow.setDefenderMove((String) aValue);
                fireTableCellUpdated(row, col);
            }
        }

        @Override
        public Class<?> getColumnClass(int col) {
            return Move.class;
        }

        @Override
        public String getColumnName(int col) {
            return NAMES[col];
        }
    }
}