package com.dotsgame.model;

import javax.swing.JOptionPane;
import java.io.Serializable;
import java.util.*;

public class GameModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Dot> dots;
    private List<Line> blueLines;  // Player 1's lines
    private List<Line> redLines;   // Player 2's lines
    private boolean player1Turn;   // true for Player 1 (blue), false for Player 2 (red)
    private Dot selectedDot;       // Currently selected dot

    public GameModel() {
        dots = new ArrayList<>();
        blueLines = new ArrayList<>();
        redLines = new ArrayList<>();
        player1Turn = true;  // Player 1 starts
    }

    public void initializeGame(int numberOfDots) {
        dots.clear();
        blueLines.clear();
        redLines.clear();
        player1Turn = true;
        selectedDot = null;

        Random rand = new Random();
        for (int i = 0; i < numberOfDots; i++) {
            int x = rand.nextInt(700) + 50;
            int y = rand.nextInt(400) + 50;
            dots.add(new Dot(x, y));
        }
    }

    public Dot findDotAt(int x, int y) {
        for (Dot dot : dots) {
            if (dot.containsPoint(x, y)) {
                return dot;
            }
        }
        return null;
    }

    public void selectDot(Dot dot) {
        if (selectedDot == null) {
            selectedDot = dot;
        } else if (selectedDot != dot) {
            Line line = new Line(selectedDot, dot);
            if (player1Turn) {
                blueLines.add(line);
            } else {
                redLines.add(line);
            }
            player1Turn = !player1Turn;
            selectedDot = null;

            if (isGameOver()) {
                displayResults();
            }
        }
    }

    public boolean isGameOver() {
        Map<Dot, Set<Dot>> graph = new HashMap<>();
        for (Dot dot : dots) {
            graph.put(dot, new HashSet<>());
        }
        for (Line line : blueLines) {
            graph.get(line.getStart()).add(line.getEnd());
            graph.get(line.getEnd()).add(line.getStart());
        }
        for (Line line : redLines) {
            graph.get(line.getStart()).add(line.getEnd());
            graph.get(line.getEnd()).add(line.getStart());
        }
        if (dots.isEmpty()) return false;

        Set<Dot> visited = new HashSet<>();
        Queue<Dot> queue = new LinkedList<>();

        Dot start = dots.get(0);
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Dot current = queue.poll();
            for (Dot neighbor : graph.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return visited.size() == dots.size();
    }

    public void displayResults() {
        double blueScore = calculateScore(blueLines);
        double redScore = calculateScore(redLines);
        double optimalScore = calculateOptimalScore();

        String winner = blueScore <= redScore ? "Player 1 (Blue)" : "Player 2 (Red)";
        String message = String.format(
                "Game Over!\n\nPlayer 1 (Blue) Score: %.2f\nPlayer 2 (Red) Score: %.2f\n\n" +
                        "Optimal Score: %.2f\n\nWinner: %s",
                blueScore, redScore, optimalScore, winner
        );

        JOptionPane.showMessageDialog(null, message, "Game Results", JOptionPane.INFORMATION_MESSAGE);
    }
    private double calculateScore(List<Line> lines) {
        double totalLength = 0;
        for (Line line : lines) {
            totalLength += line.getLength();
        }
        return totalLength;
    }

    private double calculateOptimalScore() {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < dots.size(); i++) {
            for (int j = i + 1; j < dots.size(); j++) {
                Dot d1 = dots.get(i);
                Dot d2 = dots.get(j);
                edges.add(new Edge(d1, d2, distance(d1, d2)));
            }
        }
        Collections.sort(edges, Comparator.comparingDouble(Edge::getWeight));
        Map<Dot, Dot> parent = new HashMap<>();
        for (Dot dot : dots) {
            parent.put(dot, dot);
        }

        double totalWeight = 0;
        int edgesAdded = 0;

        for (Edge edge : edges) {
            Dot root1 = find(parent, edge.getStart());
            Dot root2 = find(parent, edge.getEnd());

            if (root1 != root2) {
                union(parent, root1, root2);
                totalWeight += edge.getWeight();
                edgesAdded++;

                if (edgesAdded == dots.size() - 1) {
                    break;  // MST is complete
                }
            }
        }

        return totalWeight;
    }

    private Dot find(Map<Dot, Dot> parent, Dot dot) {
        if (parent.get(dot) != dot) {
            parent.put(dot, find(parent, parent.get(dot)));
        }
        return parent.get(dot);
    }

    private void union(Map<Dot, Dot> parent, Dot dot1, Dot dot2) {
        parent.put(dot1, dot2);
    }

    private double distance(Dot d1, Dot d2) {
        int dx = d1.getX() - d2.getX();
        int dy = d1.getY() - d2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public List<Dot> getDots() {
        return dots;
    }

    public List<Line> getBlueLines() {
        return blueLines;
    }

    public List<Line> getRedLines() {
        return redLines;
    }

    public boolean isPlayer1Turn() {
        return player1Turn;
    }

    public Dot getSelectedDot() {
        return selectedDot;
    }
}