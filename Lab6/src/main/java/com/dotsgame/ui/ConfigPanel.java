package com.dotsgame.ui;

import com.dotsgame.DotsAndLinesGame;

import javax.swing.*;
import java.awt.*;

public class ConfigPanel extends JPanel {
    private DotsAndLinesGame game;
    private JTextField dotsField;
    private JButton newGameBtn;

    public ConfigPanel(DotsAndLinesGame game) {
        this.game = game;
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel dotsLabel = new JLabel("Number of Dots:");
        dotsField = new JTextField("10", 5);
        newGameBtn = new JButton("New Game");

        add(dotsLabel);
        add(dotsField);
        add(newGameBtn);

        newGameBtn.addActionListener(e -> {
            try {
                int numberOfDots = Integer.parseInt(dotsField.getText());
                if (numberOfDots < 3) {
                    JOptionPane.showMessageDialog(game, "Please enter at least 3 dots.",
                            "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                game.newGame(numberOfDots);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(game, "Please enter a valid number.",
                        "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}