package com.example.memorengandroid.view.Pages.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.memorengandroid.R;
import com.example.memorengandroid.adapter.CustomAlertBox;
import com.example.memorengandroid.model.Game.WordleGame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Wordle extends AppCompatActivity {
    //wordle1_edit_text : yok
    //wordle2_edit_text : var ama yeri farklı
    //wordle3_edit_text : var ve yeri aynı

    public int step = 1;

    private Button[] letters;
    private EditText[][] boxes;


    Button boxDelete, approveButton;
    ImageView closeicon;
    EditText updateBox;
    int activeBoxIndex = 0;
    WordleGame game;
    CustomAlertBox customAlertBox = new CustomAlertBox(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordle);

        allBoxSetup();
        allLetterSetup();

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
                    System.out.println("xxx activeBoxIndex : " + activeBoxIndex);
                    System.out.println("xxx updateBox : " + updateBox);

                    if (updateBox.getText().toString().equals("")) {
                        System.out.println("zaten boştu...");

                        if (activeBoxIndex != 0) {
                            activeBoxIndex -= 1;
                        }

                        pointOldBox(updateBox);
                        updateBox = game.getBoxes().get(activeBoxIndex);
                        pointUpdateBox(updateBox);
                    } else {
                        System.out.println("boş değildi...");

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
        System.out.println("A : STEP : " + step);
        System.out.println("A : UPDATEBOX : " + updateBox + " : " + updateBox.getText());

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
        System.out.println("vvvvvvvvv : " + button.getText());
        System.out.println("vvvvvvvvv : " + uiControllerWithSteps(updateBox, false));

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
            EditText[] currentBoxes = boxes[step-1];
            int currentIndex = 0;

            for (EditText box : currentBoxes) {
                box.setBackgroundResource(paintBoxes(box, currentIndex));

                currentIndex += 1;
            }

            if (step < 5) {
                step += 1;

                activeBoxIndex += 1;

                System.out.println("ÇÇÇÇÇÇÇÇÇ1 : " + step + " " + activeBoxIndex);

                updateBox = game.getBoxes().get(activeBoxIndex);

                System.out.println("ÇÇÇÇÇÇÇÇÇ2 : " + updateBox);

                pointUpdateBox(updateBox);
            } else {
                customAlertBox.OpenAlertBoxReGame(Wordle.this, "Oyun bitti, tekrar denemek ister misin? ");

                System.out.println(".......END OF GAME......");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int paintBoxes(EditText editText, int indexOfLetter) {
        try {
            String compWord = "FOUND";
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
}