package com.dotsgame.ui;

import com.dotsgame.DotsAndLinesGame;
import com.dotsgame.model.Dot;
import com.dotsgame.model.GameModel;
import com.dotsgame.model.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameCanvas extends JPanel {
    private DotsAndLinesGame game;
    private Dot dragSource;
    private Point dragPoint;

    public GameCanvas(DotsAndLinesGame game) {
        this.game = game;
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLoweredBevelBorder());

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                GameModel model = game.getGameModel();
                Dot dot = model.findDotAt(e.getX(), e.getY());
                if (dot != null) {
                    dragSource = dot;
                    model.selectDot(dot);
                    repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragSource != null) {
                    dragPoint = e.getPoint();
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (dragSource != null) {
                    GameModel model = game.getGameModel();
                    Dot dot = model.findDotAt(e.getX(), e.getY());
                    if (dot != null && dot != dragSource) {
                        model.selectDot(dot);
                    }
                    dragSource = null;
                    dragPoint = null;
                    repaint();
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GameModel model = game.getGameModel();

        for (Line line : model.getBlueLines()) {
            line.draw(g2d, Color.BLUE);
        }
        for (Line line : model.getRedLines()) {
            line.draw(g2d, Color.RED);
        }

        for (Dot dot : model.getDots()) {
            dot.draw(g2d);
        }

        if (dragSource != null && dragPoint != null) {
            g2d.setColor(model.isPlayer1Turn() ? Color.BLUE : Color.RED);
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
                    new float[]{5}, 0));  // Dashed line
            g2d.drawLine(dragSource.getX(), dragSource.getY(), dragPoint.x, dragPoint.y);
        }

        Dot selectedDot = model.getSelectedDot();
        if (selectedDot != null) {
            g2d.setColor(model.isPlayer1Turn() ? Color.BLUE : Color.RED);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(selectedDot.getX() - 12, selectedDot.getY() - 12, 24, 24);
        }

        String playerText = model.isPlayer1Turn() ? "Player 1 (Blue)" : "Player 2 (Red)";
        g2d.setColor(model.isPlayer1Turn() ? Color.BLUE : Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Current Turn: " + playerText, 20, 30);

        g2d.dispose();
    }
}