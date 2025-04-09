package com.dotsgame.game;

import com.dotsgame.model.Bag;
import com.dotsgame.model.Board;
import com.dotsgame.model.Dictionary;
import com.dotsgame.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WordGame {
    private final Bag bag;
    private final Board board;
    private final Dictionary dictionary;
    private final List<Player> players = new ArrayList<>();
    private final ExecutorService executorService;

    public WordGame(int numPlayers) {
        bag = new Bag();
        board = new Board();
        dictionary = new Dictionary();

        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player("Player" + i, bag, board, dictionary));
        }

        executorService = Executors.newFixedThreadPool(numPlayers);
    }

    public void startGame() {
        System.out.println("Starting word game with " + players.size() + " players");
        System.out.println("Bag has " + bag.remainingTiles() + " tiles");

        for (Player player : players) {
            executorService.submit(player);
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Game interrupted: " + e.getMessage());
        }

        announceWinner();
    }

    private void announceWinner() {
        System.out.println("\nGame Over!");
        Map<String, Integer> scores = board.getPlayerScores();
        System.out.println("Final Scores:");

        scores.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " points"));

        String winner = board.getWinner();
        if (!scores.isEmpty()) {
            System.out.println("\nThe winner is: " + winner + " with " + scores.get(winner) + " points!");
        } else {
            System.out.println("\nNo winner!");
        }
    }
}