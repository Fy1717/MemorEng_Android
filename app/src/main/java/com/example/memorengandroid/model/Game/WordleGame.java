package com.example.memorengandroid.model.Game;

import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class WordleGame {
    public int step;
    public int score;
    public List<Button> letterButtons;
    public List<EditText> boxes;

    public EditText activeBox;



    public EditText getActiveBox() {
        return activeBox;
    }

    public void setActiveBox(EditText activeBox) {
        this.activeBox = activeBox;
    }

    public List<EditText> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<EditText> boxes) {
        this.boxes = boxes;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Button> getLetterButtons() {
        return letterButtons;
    }

    public void setLetterButtons(List<Button> letterButtons) {
        this.letterButtons = letterButtons;
    }
}

