package com.dotsgame;

import com.dotsgame.model.GameModel;
import com.dotsgame.ui.ConfigPanel;
import com.dotsgame.ui.ControlPanel;
import com.dotsgame.ui.GameCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class DotsAndLinesGame extends JFrame {

    private ConfigPanel configPanel;
    private GameCanvas gameCanvas;
    private ControlPanel controlPanel;

    private GameModel gameModel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DotsAndLinesGame().setVisible(true);
        });
    }

    public DotsAndLinesGame() {
        initUI();
    }

    private void initUI() {
        setTitle("Dots and Lines Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        gameModel = new GameModel();

        configPanel = new ConfigPanel(this);
        gameCanvas = new GameCanvas(this);
        controlPanel = new ControlPanel(this);

        add(configPanel, BorderLayout.NORTH);
        add(gameCanvas, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    public void newGame(int numberOfDots) {
        gameModel.initializeGame(numberOfDots);
        gameCanvas.repaint();
    }

    public void saveGame(File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(gameModel);
            JOptionPane.showMessageDialog(this, "Game saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving game: " + e.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadGame(File file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            gameModel = (GameModel) in.readObject();
            gameCanvas.repaint();
            JOptionPane.showMessageDialog(this, "Game loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading game: " + e.getMessage(),
                    "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void exportToPNG(File file) {
        BufferedImage image = new BufferedImage(gameCanvas.getWidth(), gameCanvas.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        gameCanvas.paint(g2d);
        g2d.dispose();

        try {
            ImageIO.write(image, "png", file);
            JOptionPane.showMessageDialog(this, "Board exported to PNG successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error exporting to PNG: " + e.getMessage(),
                    "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public GameModel getGameModel() {
        return gameModel;
    }
}