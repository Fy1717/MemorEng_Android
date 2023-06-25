package com.example.memorengandroid.model;

import java.util.List;

public class EnglishWords {
    private static EnglishWords englishWords;

    private EnglishWords() {}

    public static EnglishWords getInstance() {
        if (englishWords == null) {
            englishWords = new EnglishWords();
        }

        return englishWords;
    }

    private List<EnglishWord> allWords;

    public List<EnglishWord> getAllWords() {
        return allWords;
    }

    public void setAllWords(List<EnglishWord> allWords) {
        this.allWords = allWords;
    }
}
