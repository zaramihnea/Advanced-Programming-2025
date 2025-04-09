package com.dotsgame.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Board {
    private final List<String> submittedWords = new ArrayList<>();
    private final Map<String, Integer> playerScores = new ConcurrentHashMap<>();

    public synchronized void submitWord(String playerName, String word, int points) {
        submittedWords.add(word);
        playerScores.put(playerName, playerScores.getOrDefault(playerName, 0) + points);
        System.out.println(playerName + " submitted word: " + word + " for " + points + " points");
    }

    public Map<String, Integer> getPlayerScores() {
        return new HashMap<>(playerScores);
    }

    public String getWinner() {
        if (playerScores.isEmpty()) {
            return "No winner";
        }

        return playerScores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }
}