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
    public int step = 1;
    EditText line1Box1, line1Box2, line1Box3, line1Box4, line1Box5,
            line2Box1, line2Box2, line2Box3, line2Box4, line2Box5,
            line3Box1, line3Box2, line3Box3, line3Box4, line3Box5,
            line4Box1, line4Box2, line4Box3, line4Box4, line4Box5,
            line5Box1, line5Box2, line5Box3, line5Box4, line5Box5;
    Button letterQ, letterW, letterE, letterR, letterT, letterY, letterU, letterI, letterO, letterP,
            letterA, letterS, letterD, letterF, letterG, letterH, letterJ, letterK, letterL,
            letterZ, letterX, letterC, letterV, letterB, letterN, letterM, letterDelete,
            approveButton;

    List<Button> letterButtons = new ArrayList<Button>();
    ImageView closeicon;
    EditText updateBox;
    int activeBoxIndex = 0;
    WordleGame game;
    List<EditText> letterBoxesForGame = new ArrayList<EditText>();
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
        letterQ = findViewById(R.id.textViewQ);
        letterW = findViewById(R.id.textViewW);
        letterE = findViewById(R.id.textViewE);
        letterR = findViewById(R.id.textViewR);
        letterT = findViewById(R.id.textViewT);
        letterY = findViewById(R.id.textViewY);
        letterU = findViewById(R.id.textViewU);
        letterI = findViewById(R.id.textViewI);
        letterO = findViewById(R.id.textViewO);
        letterP = findViewById(R.id.textViewP);

        letterA = findViewById(R.id.textViewA);
        letterS = findViewById(R.id.textViewS);
        letterD = findViewById(R.id.textViewD);
        letterF = findViewById(R.id.textViewF);
        letterG = findViewById(R.id.textViewG);
        letterH = findViewById(R.id.textViewH);
        letterJ = findViewById(R.id.textViewJ);
        letterK = findViewById(R.id.textViewK);
        letterL = findViewById(R.id.textViewL);

        letterZ = findViewById(R.id.textViewZ);
        letterX = findViewById(R.id.textViewX);
        letterC = findViewById(R.id.textViewC);
        letterV = findViewById(R.id.textViewV);
        letterB = findViewById(R.id.textViewB);
        letterN = findViewById(R.id.textViewN);
        letterM = findViewById(R.id.textViewM);

        letterButtons = Arrays.asList(
                letterA, letterB, letterC, letterD, letterE, letterF, letterG, letterH,
                letterI, letterJ, letterK, letterL, letterM, letterN, letterO, letterP,
                letterR, letterS, letterT, letterU, letterV, letterY, letterZ, letterX,
                letterQ, letterW);

        approveButton = findViewById(R.id.approve_button);
        approveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stepController();
            }
        });

        setUpGame();

        letterDelete = findViewById(R.id.delete_button);

        letterDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (uiControllerWithSteps(updateBox, true)) {
                    System.out.println("xxx activeBoxIndex : " + activeBoxIndex);
                    System.out.println("xxx updateBox : " + updateBox);

                    if (activeBoxIndex >= 1) {
                        if (activeBoxIndex == 24 || activeBoxIndex == 23 || activeBoxIndex == 22
                                || activeBoxIndex == 21 || activeBoxIndex == 20) {
                            System.out.println("LLLLLLLL");

                            updateBox = game.getBoxes().get(activeBoxIndex);
                            updateBox.setText("");

                            activeBoxIndex -= 1;
                        } else {
                            System.out.println("SSSSSSS");
                            activeBoxIndex -= 1;

                            updateBox = game.getBoxes().get(activeBoxIndex);
                            updateBox.setText("");
                        }
                    }
                }
            }
        });
    }

    private void allBoxSetup() {
        line1Box1 = findViewById(R.id.line1l1);
        line1Box2 = findViewById(R.id.line1l2);
        line1Box3 = findViewById(R.id.line1l3);
        line1Box4 = findViewById(R.id.line1l4);
        line1Box5 = findViewById(R.id.line1l5);

        line2Box1 = findViewById(R.id.line2l1);
        line2Box2 = findViewById(R.id.line2l2);
        line2Box3 = findViewById(R.id.line2l3);
        line2Box4 = findViewById(R.id.line2l4);
        line2Box5 = findViewById(R.id.line2l5);

        line3Box1 = findViewById(R.id.line3l1);
        line3Box2 = findViewById(R.id.line3l2);
        line3Box3 = findViewById(R.id.line3l3);
        line3Box4 = findViewById(R.id.line3l4);
        line3Box5 = findViewById(R.id.line3l5);

        line4Box1 = findViewById(R.id.line4l1);
        line4Box2 = findViewById(R.id.line4l2);
        line4Box3 = findViewById(R.id.line4l3);
        line4Box4 = findViewById(R.id.line4l4);
        line4Box5 = findViewById(R.id.line4l5);

        line5Box1 = findViewById(R.id.line5l1);
        line5Box2 = findViewById(R.id.line5l2);
        line5Box3 = findViewById(R.id.line5l3);
        line5Box4 = findViewById(R.id.line5l4);
        line5Box5 = findViewById(R.id.line5l5);

        letterBoxesForGame = Arrays.asList(
                line1Box1, line1Box2, line1Box3, line1Box4, line1Box5,
                line2Box1, line2Box2, line2Box3, line2Box4, line2Box5,
                line3Box1, line3Box2, line3Box3, line3Box4, line3Box5,
                line4Box1, line4Box2, line4Box3, line4Box4, line4Box5,
                line5Box1, line5Box2, line5Box3, line5Box4, line5Box5
        );

        updateBox = line1Box1;
    }

    public void setUpGame() {

        for (Button button : letterButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(button.getText().toString());

                    if (uiControllerWithSteps(updateBox, false)) {
                        updateBox.setText(button.getText().toString());

                        if (activeBoxIndex < game.getBoxes().size() - 1) {
                            activeBoxIndex += 1;
                        }

                        updateBox = game.getBoxes().get(activeBoxIndex);
                    }
                }
            });
        }

        game = new WordleGame();
        game.setLetterButtons(letterButtons);
        game.setBoxes(letterBoxesForGame);
        game.setActiveBox(letterBoxesForGame.get(0));

        System.out.println("XXXX : " + game.getLetterButtons().size());
        System.out.println("XXXX : " + game.getLetterButtons().get(2).getText().toString());

        System.out.println("YYYY : " + game.getBoxes().size());
        System.out.println("YYYY : " + game.getBoxes().get(activeBoxIndex).getText().toString());
    }

    public Boolean uiControllerWithSteps(EditText updateBox, boolean forDelete) {
        System.out.println("A : STEP : " + step);
        System.out.println("A : UPDATEBOX : " + updateBox + " : " + updateBox.getText());

        if (step == 1 && !forDelete) {
            if (updateBox == line1Box1 || updateBox == line1Box2 || updateBox == line1Box3 ||
                    updateBox == line1Box4 || updateBox == line1Box5) {
                return true;
            }
        } else if (step == 1 && forDelete) {
            if (updateBox == line1Box1 || updateBox == line1Box2 || updateBox == line1Box3 ||
                    updateBox == line1Box4 || updateBox == line1Box5 || updateBox == line2Box1) {
                return true;
            }
        }

        if (step == 2 && !forDelete) {
            if (updateBox == line2Box1 || updateBox == line2Box2 || updateBox == line2Box3 ||
                    updateBox == line2Box4 || updateBox == line2Box5) {
                return true;
            }
        } else if (step == 2 && forDelete) {
            if (updateBox == line2Box2 || updateBox == line2Box3 ||
                    updateBox == line2Box4 || updateBox == line2Box5 || updateBox == line3Box1) {
                return true;
            }
        }

        if (step == 3 && !forDelete) {
            if (updateBox == line3Box1 || updateBox == line3Box2 || updateBox == line3Box3 ||
                    updateBox == line3Box4 || updateBox == line3Box5) {
                return true;
            }
        } else if (step == 3 && forDelete) {
            if (updateBox == line3Box2 || updateBox == line3Box3 ||
                    updateBox == line3Box4 || updateBox == line3Box5 || updateBox == line4Box1) {
                return true;
            }
        }

        if (step == 4 && !forDelete) {
            if (updateBox == line4Box1 || updateBox == line4Box2 || updateBox == line4Box3 ||
                    updateBox == line4Box4 || updateBox == line4Box5) {
                return true;
            }
        } else if (step == 4 && forDelete) {
            if (updateBox == line4Box2 || updateBox == line4Box3 ||
                    updateBox == line4Box4 || updateBox == line4Box5 || updateBox == line5Box1) {
                return true;
            }
        }

        if (step == 5 && !forDelete) {
            if (updateBox == line5Box1 || updateBox == line5Box2 || updateBox == line5Box3 ||
                    updateBox == line5Box4 || updateBox == line5Box5) {
                return true;
            }
        } else if (step == 5 && forDelete) {
            if (updateBox == line5Box2 || updateBox == line5Box3 ||
                    updateBox == line5Box4 || updateBox == line5Box5) {
                return true;
            }
        }

        return false;
    }

    public void stepController() {
        System.out.println("STEP : " + step);
        System.out.println("UPDATEBOX : " + updateBox + " : " + updateBox.getText());

        if (step == 1) {
            if (updateBox == line2Box1) {
                step = 2;
            }
        }

        if (step == 2) {
            if (updateBox == line3Box1) {
                step = 3;
            }
        }

        if (step == 3) {
            if (updateBox == line4Box1) {
                step = 4;
            }
        }

        if (step == 4) {
            if (updateBox == line5Box1) {
                step = 5;
            }
        }

        if (step == 5) {
            if (updateBox == line5Box5) {
                System.out.println("END OF GAME");
            }
        }
    }
}