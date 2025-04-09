package com.dotsgame.player;

import com.dotsgame.model.Bag;
import com.dotsgame.model.Board;
import com.dotsgame.model.Dictionary;
import com.dotsgame.model.Tile;

import java.util.*;

public class Player implements Runnable {
    private final String name;
    private final List<Tile> tiles = new ArrayList<>();
    private final Bag bag;
    private final Board board;
    private final Dictionary dictionary;
    private final Random random = new Random();
    private boolean gameOver = false;

    public Player(String name, Bag bag, Board board, Dictionary dictionary) {
        this.name = name;
        this.bag = bag;
        this.board = board;
        this.dictionary = dictionary;
    }

    @Override
    public void run() {
        System.out.println(name + " starts playing");

        tiles.addAll(bag.extractTiles(7));
        System.out.println(name + " drew initial 7 tiles: " + tilesToString());

        while (!gameOver) {
            if (bag.isEmpty() && !canFormAnyWord()) {
                gameOver = true;
                continue;
            }

            String word = tryToFormWord();

            if (word != null) {
                int points = calculatePoints(word);
                board.submitWord(name, word, points);

                removeTilesForWord(word);

                List<Tile> newTiles = bag.extractTiles(word.length());
                tiles.addAll(newTiles);

                System.out.println(name + " drew " + newTiles.size() + " new tiles, now has: " + tilesToString());
            } else {
                System.out.println(name + " could not form a word, discarding tiles");
                tiles.clear();

                List<Tile> newTiles = bag.extractTiles(7);
                tiles.addAll(newTiles);

                if (tiles.isEmpty()) {
                    gameOver = true;
                    System.out.println(name + " has no more tiles and bag is empty");
                } else {
                    System.out.println(name + " drew " + newTiles.size() + " new tiles, now has: " + tilesToString());
                }
            }

            try {
                Thread.sleep(random.nextInt(300) + 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println(name + " has finished playing");
    }

    private boolean canFormAnyWord() {
        return tryToFormWord() != null;
    }

    private String tryToFormWord() {
        Map<Character, Integer> letterCounts = new HashMap<>();
        for (Tile tile : tiles) {
            char letter = tile.getLetter();
            letterCounts.put(letter, letterCounts.getOrDefault(letter, 0) + 1);
        }

        for (String word : dictionary.getAllWords()) {
            if (canFormWord(word, new HashMap<>(letterCounts))) {
                return word;
            }
        }

        return null;
    }

    private boolean canFormWord(String word, Map<Character, Integer> letterCounts) {
        for (char c : word.toCharArray()) {
            int count = letterCounts.getOrDefault(c, 0);
            if (count <= 0) {
                return false;
            }
            letterCounts.put(c, count - 1);
        }
        return true;
    }

    private int calculatePoints(String word) {
        int points = 0;
        Map<Character, List<Tile>> tilesByLetter = new HashMap<>();
        for (Tile tile : tiles) {
            char letter = tile.getLetter();
            tilesByLetter.putIfAbsent(letter, new ArrayList<>());
            tilesByLetter.get(letter).add(tile);
        }

        for (char c : word.toCharArray()) {
            List<Tile> availableTiles = tilesByLetter.get(c);
            if (availableTiles != null && !availableTiles.isEmpty()) {
                Tile tile = availableTiles.remove(0);
                points += tile.getPoints();
            }
        }

        return points;
    }

    private void removeTilesForWord(String word) {
        Map<Character, Integer> letterCounts = new HashMap<>();

        for (char c : word.toCharArray()) {
            letterCounts.put(c, letterCounts.getOrDefault(c, 0) + 1);
        }

        Iterator<Tile> it = tiles.iterator();
        while (it.hasNext()) {
            Tile tile = it.next();
            char letter = tile.getLetter();
            int count = letterCounts.getOrDefault(letter, 0);
            if (count > 0) {
                it.remove();
                letterCounts.put(letter, count - 1);
            }
        }
    }

    private String tilesToString() {
        StringBuilder sb = new StringBuilder();
        for (Tile tile : tiles) {
            sb.append(tile.getLetter());
        }
        return sb.toString();
    }

    public String getName() {
        return name;
    }
}