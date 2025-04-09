package com.dotsgame.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Dictionary {
    private final Set<String> words;

    public Dictionary() {
        words = new HashSet<>();
        loadDictionary();
    }

    private void loadDictionary() {
        String[] sampleWords = {
                "ABLE", "ABOUT", "ACT", "ADD", "AIR", "ALL", "AND", "BAG", "BALL",
                "BAT", "BED", "BIG", "BOOK", "BOX", "BOY", "BUG", "CAR", "CAT",
                "CUT", "DAY", "DOG", "EAT", "EGG", "END", "EYE", "FAR", "FUN",
                "GAP", "GAS", "GET", "HAT", "HEN", "HIT", "ICE", "JOB", "KEY",
                "LET", "LOG", "MAN", "MAP", "MIX", "NET", "NEW", "NOW", "ODD",
                "OFF", "OIL", "OLD", "OUT", "PEN", "PIG", "POT", "RED", "RUN",
                "SAY", "SEA", "SEE", "SET", "SIT", "SIX", "SKY", "SON", "SUN",
                "TEN", "THE", "TIE", "TOP", "TRY", "USE", "VAN", "WAR", "WAY",
                "WHO", "WHY", "WIN", "YES", "YET", "YOU", "ZOO"
        };

        Collections.addAll(words, sampleWords);
    }

    public boolean isValidWord(String word) {
        return words.contains(word.toUpperCase());
    }

    public Set<String> getAllWords() {
        return new HashSet<>(words);
    }
}