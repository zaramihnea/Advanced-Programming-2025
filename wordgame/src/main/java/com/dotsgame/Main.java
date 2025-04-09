package com.dotsgame;

import com.dotsgame.game.WordGame;

public class Main {
    public static void main(String[] args) {
        int numPlayers = 3;

        if (args.length > 0) {
            try {
                numPlayers = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number of players. Using default: 3");
            }
        }

        WordGame game = new WordGame(numPlayers);
        game.startGame();
    }
}