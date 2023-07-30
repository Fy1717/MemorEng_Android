package com.example.memorengandroid.view.Pages.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.memorengandroid.R;
import com.example.memorengandroid.adapter.CustomAlertBox;
import com.example.memorengandroid.model.EnglishWord;
import com.example.memorengandroid.model.EnglishWords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;

public class MatchWords extends AppCompatActivity {
    Button option1, option2, option3, option4, option5, option6, option7, option8;
    EnglishWords englishWordList = EnglishWords.getInstance();
    List<Button> options = new ArrayList<Button>();
    ImageView closeicon;

    CustomAlertBox customAlertBox = new CustomAlertBox(this);
    Button selectedQuestion = null;
    Button selectedAnswer = null;
    int trueCount = 0;
    EnglishWord word1, word2, word3, word4, selectedWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_words);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        option5 = findViewById(R.id.option5);
        option6 = findViewById(R.id.option6);
        option7 = findViewById(R.id.option7);
        option8 = findViewById(R.id.option8);

        if (englishWordList != null &&
                englishWordList.getAllWords() != null &&
                englishWordList.getAllWords().size() > 10) {
            setUpGame();

            options.add(option1);
            options.add(option2);
            options.add(option3);
            options.add(option4);
            options.add(option5);
            options.add(option6);
            options.add(option7);
            options.add(option8);

            setClickListeners();
        }

        closeicon = (ImageView) findViewById(R.id.closeicon);

        closeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAlertBox.OpenAlertBoxExit(MatchWords.this, "Anasayfaya dönmek istediğinize emin misiniz? ", true);
            }
        });
    }

    public void setUpGame() {
        trueCount = 0;

        word1 = getRandomWordAndAnswer();
        word2 = getRandomWordAndAnswer();
        word3 = getRandomWordAndAnswer();
        word4 = getRandomWordAndAnswer();

        option1.setText(word1.getWord().toUpperCase(Locale.ROOT));
        option2.setText(word2.getWord().toUpperCase(Locale.ROOT));
        option3.setText(word3.getWord().toUpperCase(Locale.ROOT));
        option4.setText(word4.getWord().toUpperCase(Locale.ROOT));

        //option5.setText(word1.getTranslations().get(0).toUpperCase(Locale.ROOT));
        //option6.setText(word2.getTranslations().get(0).toUpperCase(Locale.ROOT));
        //option7.setText(word3.getTranslations().get(0).toUpperCase(Locale.ROOT));
        //option8.setText(word4.getTranslations().get(0).toUpperCase(Locale.ROOT));

        //----------------------------------------------------------------

        List<String> translationsForOptions = new ArrayList<>();
        translationsForOptions.add(word1.getTranslations().get(0).toUpperCase(Locale.ROOT));
        translationsForOptions.add(word2.getTranslations().get(0).toUpperCase(Locale.ROOT));
        translationsForOptions.add(word3.getTranslations().get(0).toUpperCase(Locale.ROOT));
        translationsForOptions.add(word4.getTranslations().get(0).toUpperCase(Locale.ROOT));

        Collections.shuffle(translationsForOptions);

        option5.setText(translationsForOptions.get(0).toUpperCase(Locale.ROOT));
        option6.setText(translationsForOptions.get(1).toUpperCase(Locale.ROOT));
        option7.setText(translationsForOptions.get(2).toUpperCase(Locale.ROOT));
        option8.setText(translationsForOptions.get(3).toUpperCase(Locale.ROOT));

        //----------------------------------------------------------------

        for (Button option : options) {
            option.setVisibility(View.VISIBLE);
            option.setBackgroundResource(R.drawable.option);
        }
    }

    public int getRandomNumber(int minNum, int maxNum) {
        Random random = new Random();
        int result = random.nextInt(maxNum - minNum);

        return result;
    }

    public EnglishWord getRandomWordAndAnswer() {
        int lengthOfWordList = englishWordList.getAllWords().size();

        int wordIndex = getRandomNumber(0, lengthOfWordList);

        return englishWordList.getAllWords().get(wordIndex);
    }

    public void setClickListeners() {
        for (Button option : options) {
            option.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (option == option1 || option == option2 || option == option3 || option == option4) {
                        selectedQuestion = option;

                        if (option == option1) {
                            selectedWord = word1;
                            option2.setBackgroundResource(R.drawable.option);
                            option3.setBackgroundResource(R.drawable.option);
                            option4.setBackgroundResource(R.drawable.option);
                        } else if (option == option2) {
                            selectedWord = word2;
                            option1.setBackgroundResource(R.drawable.option);
                            option3.setBackgroundResource(R.drawable.option);
                            option4.setBackgroundResource(R.drawable.option);
                        } else if (option == option3) {
                            selectedWord = word3;
                            option2.setBackgroundResource(R.drawable.option);
                            option1.setBackgroundResource(R.drawable.option);
                            option4.setBackgroundResource(R.drawable.option);
                        } else {
                            selectedWord = word4;
                            option2.setBackgroundResource(R.drawable.option);
                            option3.setBackgroundResource(R.drawable.option);
                            option1.setBackgroundResource(R.drawable.option);
                        }

                        option5.setBackgroundResource(R.drawable.option);
                        option6.setBackgroundResource(R.drawable.option);
                        option7.setBackgroundResource(R.drawable.option);
                        option8.setBackgroundResource(R.drawable.option);

                        option.setBackgroundResource(R.drawable.truebutton);

                        System.out.println("QUESTION : " + option.getText());
                    }

                    if (selectedQuestion != null) {
                        if ((option == option5 || option == option6 || option == option7 || option == option8)) {
                            selectedAnswer = option;

                            if (selectedWord.getTranslations().get(0).toUpperCase(Locale.ROOT).equals(
                                    selectedAnswer.getText().toString().toUpperCase(Locale.ROOT))) {
                                option.setBackgroundResource(R.drawable.truebutton);
                                System.out.println("TRUE ANSWER : " + option.getText());

                                trueCount += 1;

                                System.out.println("TRUE COUNT : " + trueCount);

                                selectedQuestion.setVisibility(View.GONE);
                                selectedAnswer.setVisibility(View.GONE);

                                selectedWord = null;
                                selectedQuestion = null;
                                selectedAnswer = null;

                                if (trueCount == 4) {
                                    setUpGame();
                                }
                            } else {
                                System.out.println("FALSE ANSWER : " + selectedAnswer.getText());

                                Toast.makeText(getApplicationContext(), "Yanlış Eşleştirme", Toast.LENGTH_SHORT).show();

                                selectedQuestion.setBackgroundResource(R.drawable.option);
                                selectedAnswer.setBackgroundResource(R.drawable.option);

                                selectedWord = null;
                                selectedQuestion = null;
                                selectedAnswer = null;
                            }
                        }
                    } else {
                        System.out.println("SORU SEÇİLMEDİ");
                    }
                }
            });
        }
    }
}