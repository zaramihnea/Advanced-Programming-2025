package com.dotsgame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Bag {
    private final List<Tile> tiles;
    private final Random random;

    public Bag() {
        tiles = new ArrayList<>();
        random = new Random();
        initializeBag();
    }

    private void initializeBag() {
        for (char c = 'A'; c <= 'Z'; c++) {
            int points = random.nextInt(10) + 1;
            for (int i = 0; i < 10; i++) {
                tiles.add(new Tile(c, points));
            }
        }
        Collections.shuffle(tiles);
    }

    public synchronized List<Tile> extractTiles(int count) {
        List<Tile> extracted = new ArrayList<>();
        int tilesToExtract = Math.min(count, tiles.size());

        for (int i = 0; i < tilesToExtract; i++) {
            extracted.add(tiles.remove(0));
        }

        return extracted;
    }

    public synchronized boolean isEmpty() {
        return tiles.isEmpty();
    }

    public synchronized int remainingTiles() {
        return tiles.size();
    }
}