package com.example.memorengandroid.view.Pages.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.memorengandroid.R;
import com.example.memorengandroid.adapter.CustomAlertBox;
import com.example.memorengandroid.model.Game.WordleGame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Wordle extends AppCompatActivity {
    public int step = 1;

    private Button[] letters;
    private EditText[][] boxes;


    Button boxDelete, approveButton;
    ImageView closeicon;
    EditText updateBox;
    int activeBoxIndex = 0;
    WordleGame game;
    CustomAlertBox customAlertBox = new CustomAlertBox(this);
    View keyboard;
    TextView reGameText;
    String compWord = "BREAD";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordle);

        allBoxSetup();
        allLetterSetup();

        keyboard = findViewById(R.id.keyboard);
        reGameText = findViewById(R.id.reGameText);

        reGameText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wordle.this, Wordle.class);
                startActivity(intent);
            }
        });

        closeicon = (ImageView) findViewById(R.id.closeicon);
        closeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAlertBox.OpenAlertBoxExit(Wordle.this, "Anasayfaya dönmek istediğinizden emin misiniz? ", true);
            }
        });
    }

    private void allLetterSetup() {
        letters = new Button[]{
                findViewById(R.id.textViewQ), findViewById(R.id.textViewW), findViewById(R.id.textViewE),
                findViewById(R.id.textViewR), findViewById(R.id.textViewT), findViewById(R.id.textViewY),
                findViewById(R.id.textViewU), findViewById(R.id.textViewI), findViewById(R.id.textViewO),
                findViewById(R.id.textViewP), findViewById(R.id.textViewA), findViewById(R.id.textViewS),
                findViewById(R.id.textViewD), findViewById(R.id.textViewF), findViewById(R.id.textViewG),
                findViewById(R.id.textViewH), findViewById(R.id.textViewJ), findViewById(R.id.textViewK),
                findViewById(R.id.textViewL), findViewById(R.id.textViewZ), findViewById(R.id.textViewX),
                findViewById(R.id.textViewC), findViewById(R.id.textViewV), findViewById(R.id.textViewB),
                findViewById(R.id.textViewN), findViewById(R.id.textViewM)
        };

        for (int i = 0; i < letters.length; i++) {
            final int index = i;
            letters[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLetterClick(letters[index]);
                }
            });
        }

        approveButton = findViewById(R.id.approve_button);
        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isApproved = true;

                for (EditText box : boxes[step - 1]) {
                    if (box.getText().toString().equals("")) {
                        isApproved = false;
                    }
                }

                if (isApproved) {
                    animateBoxes();
                }
            }
        });
        setUpGame();

        boxDelete = findViewById(R.id.delete_button);

        boxDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (uiControllerWithSteps(updateBox, true)) {
                    if (updateBox.getText().toString().equals("")) {
                        if (activeBoxIndex != 0) {
                            activeBoxIndex -= 1;
                        }

                        pointOldBox(updateBox);
                        updateBox = game.getBoxes().get(activeBoxIndex);
                        pointUpdateBox(updateBox);
                    } else {
                        updateBox.setText("");

                        if (activeBoxIndex != 0) {
                            activeBoxIndex -= 1;
                        }

                        pointOldBox(updateBox);
                        updateBox = game.getBoxes().get(activeBoxIndex);
                        pointUpdateBox(updateBox);
                    }
                }
            }
        });
    }

    private void allBoxSetup() {
        boxes = new EditText[][]{
                {findViewById(R.id.line1l1), findViewById(R.id.line1l2), findViewById(R.id.line1l3), findViewById(R.id.line1l4), findViewById(R.id.line1l5)},
                {findViewById(R.id.line2l1), findViewById(R.id.line2l2), findViewById(R.id.line2l3), findViewById(R.id.line2l4), findViewById(R.id.line2l5)},
                {findViewById(R.id.line3l1), findViewById(R.id.line3l2), findViewById(R.id.line3l3), findViewById(R.id.line3l4), findViewById(R.id.line3l5)},
                {findViewById(R.id.line4l1), findViewById(R.id.line4l2), findViewById(R.id.line4l3), findViewById(R.id.line4l4), findViewById(R.id.line4l5)},
                {findViewById(R.id.line5l1), findViewById(R.id.line5l2), findViewById(R.id.line5l3), findViewById(R.id.line5l4), findViewById(R.id.line5l5)}
        };

        updateBox = boxes[0][0];
        pointUpdateBox(updateBox);
    }

    public void setUpGame() {
        game = new WordleGame();
        game.setLetterButtons(Arrays.asList(letters));

        List<EditText> boxesList = new ArrayList<>();
        for (EditText[] boxArray : boxes) {
            boxesList.addAll(Arrays.asList(boxArray));
        }

        game.setBoxes(boxesList);
        game.setActiveBox(boxes[0][0]);
    }

    public Boolean uiControllerWithSteps(EditText updateBox, boolean forDelete) {
        if (forDelete) {
            for (EditText box : boxes[step-1]) {
                if (updateBox == box && updateBox != boxes[step - 1][0]) {
                    return true;
                }
            }

            return false;
        } else {
            for (EditText box : boxes[step-1]) {
                if (updateBox == box) {
                    return true;
                }
            }

            return false;
        }
    }

    private void onLetterClick(Button button) {
        if (uiControllerWithSteps(updateBox, false)) {
            updateBox.setText(button.getText().toString());

            if (updateBox != boxes[step-1][4]) {
                if (activeBoxIndex < game.getBoxes().size() - 1) {
                    activeBoxIndex += 1;
                }

                pointOldBox(updateBox);
                updateBox = game.getBoxes().get(activeBoxIndex);
                pointUpdateBox(updateBox);
            }
        }
    }

    public void animateBoxes() {
        try {
            String currentAnswer = "";
            EditText[] currentBoxes = boxes[step-1];
            int currentIndex = 0;

            for (EditText box : currentBoxes) {
                int resultColor = paintBoxes(box, currentIndex);
                box.setBackgroundResource(resultColor);

                currentAnswer += box.getText().toString();
                updateKeyboard(box.getText().toString(), resultColor);

                currentIndex += 1;
            }

            if (currentAnswer.equals(compWord)) {
                endOfTheGame();
            }

            if (step < 5) {
                step += 1;

                activeBoxIndex += 1;

                updateBox = game.getBoxes().get(activeBoxIndex);
                pointUpdateBox(updateBox);
            } else {
                endOfTheGame();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int paintBoxes(EditText editText, int indexOfLetter) {
        try {
            String controlLetter = editText.getText().toString();

            if (compWord.charAt(indexOfLetter) == controlLetter.charAt(0)) {
                return R.drawable.wordle3_edit_text;
            } else if (compWord.indexOf(controlLetter) > -1) {
                return R.drawable.wordle1_edit_text;
            } else {
                return R.drawable.wordle2_edit_text;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.drawable.wordle_edit_text;
    }

    public void pointUpdateBox(EditText updateBox) {
        updateBox.setBackgroundResource(R.drawable.wordle4_edit_text);
    }

    public void pointOldBox(EditText oldBox) {
        oldBox.setBackgroundResource(R.drawable.wordle_edit_text);
    }

    public void updateKeyboard(String letterFromController, int colorOfLetter) {
        String letterOfButton;

        for (Button letterButton : letters) {
            letterOfButton = letterButton.getText().toString();

            if (letterFromController.equals(letterOfButton)) {
                letterButton.setBackgroundResource(colorOfLetter);

                if (R.drawable.wordle2_edit_text == colorOfLetter) {
                    letterButton.setClickable(false);
                    letterButton.setTextColor(Color.parseColor("#aaaaaa"));
                }
            }
        }
    }

    public void endOfTheGame () {
        keyboard.setVisibility(View.GONE);
        approveButton.setVisibility(View.GONE);
        reGameText.setVisibility(View.VISIBLE);

        customAlertBox.OpenAlertBoxReGame(Wordle.this, "Oyun bitti, tekrar denemek ister misin?");

        System.out.println(".......END OF GAME......");
    }

    @Override
    public void onBackPressed() {}
}