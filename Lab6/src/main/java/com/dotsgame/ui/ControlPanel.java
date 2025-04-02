package com.dotsgame.ui;

import com.dotsgame.DotsAndLinesGame;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ControlPanel extends JPanel {
    private DotsAndLinesGame game;
    private JButton loadBtn;
    private JButton saveBtn;
    private JButton exportBtn;
    private JButton exitBtn;

    public ControlPanel(DotsAndLinesGame game) {
        this.game = game;
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

        loadBtn = new JButton("Load");
        saveBtn = new JButton("Save");
        exportBtn = new JButton("Export PNG");
        exitBtn = new JButton("Exit");

        add(loadBtn);
        add(saveBtn);
        add(exportBtn);
        add(exitBtn);

        loadBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(game) == JFileChooser.APPROVE_OPTION) {
                game.loadGame(fileChooser.getSelectedFile());
            }
        });

        saveBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(game) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(".ser")) {
                    file = new File(file.getPath() + ".ser");
                }
                game.saveGame(file);
            }
        });

        exportBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(game) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(".png")) {
                    file = new File(file.getPath() + ".png");
                }
                game.exportToPNG(file);
            }
        });

        exitBtn.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(game, "Are you sure you want to exit?",
                    "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}