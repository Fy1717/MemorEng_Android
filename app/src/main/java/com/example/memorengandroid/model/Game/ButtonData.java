package com.example.memorengandroid.model.Game;

import android.graphics.Color;
import android.widget.Button;

public class ButtonData {
    public Button button;
    public String letter;
    public int color;


    public ButtonData(Button button) {
        this.button = button;
        this.color = Color.parseColor("#ffffff");
        this.letter = button.getText().toString();
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getLetter() {
        return letter;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
