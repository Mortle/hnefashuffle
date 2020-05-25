package com.hnefashuffle;

import com.hnefashuffle.gui.Table;

import javax.swing.*;

public class Hnefashuffle {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Table::new);
    }
}
