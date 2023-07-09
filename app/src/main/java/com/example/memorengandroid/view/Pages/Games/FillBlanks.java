package com.example.memorengandroid.view.Pages.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.memorengandroid.R;
import com.example.memorengandroid.adapter.CustomAlertBox;
import com.example.memorengandroid.model.EnglishWord;
import com.example.memorengandroid.model.EnglishWords;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FillBlanks extends AppCompatActivity {
    public static TextView question, countOfQuestion;
    public static View showAnswer, getHintOfAnswer;
    public static EditText usersAnswer;
    public static Button approve;
    public static ImageView closeicon;
    public static String answerOfQuestion = "";
    public static String answerOfUser = "";
    int totalAnsweredWords = 1;

    CustomAlertBox customAlertBox = new CustomAlertBox(this);
    EnglishWords englishWordList = EnglishWords.getInstance();
    EnglishWord englishWord;
    String lastWrongAnsweredQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_blanks);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#f0f1f2"));

        question = (TextView) findViewById(R.id.question);
        showAnswer = (View) findViewById(R.id.viewQuestionsAnswer);
        getHintOfAnswer = (View) findViewById(R.id.getQuestionHintArea);
        usersAnswer = (EditText)findViewById(R.id.answer);
        approve = (Button)findViewById(R.id.approve);
        closeicon = (ImageView) findViewById(R.id.closeicon);
        countOfQuestion = findViewById(R.id.count_of_question);

        if (englishWordList.getAllWords() != null) {
            setNewQuestionToUi();

            Log.i("GAME-FB", "TOPLAM SORU SAYISI : " + englishWordList.getAllWords().size());
        } else {
            finish();
        }

        closeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAlertBox.OpenAlertBoxExit(FillBlanks.this, "Anasayfaya dönmek istediğinizden emin misiniz? ", true);
            }
        });

        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersAnswer.setText(answerOfQuestion);
                usersAnswer.setSelection(usersAnswer.getText().length());
                answerOfUser = answerOfQuestion;
            }
        });

        getHintOfAnswer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getQuestionHint();
            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerOfUser = usersAnswer.getText().toString().toUpperCase(Locale.ROOT).trim();

                Log.i("GAME-FB", "USER ANSWER : " + answerOfUser);
                Log.i("GAME-FB", "REAL ANSWER : " + answerOfQuestion);

                usersAnswer.setHint("Cevabınız..");

                AnswerTheQuestion();
            }
        });

        setNewQuestionToUi();
    }

    public Boolean AnswerTheQuestion() {
        usersAnswer.setHint("Cevabınız..");

        if (getResultOfAnswer()) {
            Log.i("GAME-FB", "CEVAP DOĞRU");

            usersAnswer.setHint("Cevabınız..");
        } else {
            FillBlanks.this.runOnUiThread(new Runnable() {
                public void run() {
                    lastWrongAnsweredQuestion = "Yanlış Cevap..\n\n Soru: " + englishWord.getWord() +
                            "\nCevap: " + englishWord.getTranslations().get(0) +
                            "\nCevabınız: " + answerOfUser;

                    customAlertBox.OpenAlertBoxError(FillBlanks.this, lastWrongAnsweredQuestion);
                }
            });

            usersAnswer.setText("");
            usersAnswer.requestFocus();
            showAnswer.setVisibility(View.VISIBLE);
        }

        if (totalAnsweredWords > 19) {
            customAlertBox.OpenAlertBoxExit(FillBlanks.this, "Soruları tamamladınız.. Tekrarlamak ister misiniz? ", false);
            totalAnsweredWords = 0;
        } else {
            setNewQuestionToUi();
        }

        totalAnsweredWords += 1;
        countOfQuestion.setText(String.valueOf(totalAnsweredWords));

        Log.i("GAME-FB", String.valueOf(totalAnsweredWords));

        return true;
    }

    public EnglishWord getRandomWord() {
        try {
            Random r = new Random();

            int low = 0;
            int high = englishWordList.getAllWords().size() - 1;
            int result = r.nextInt(high-low) + low;

            return englishWordList.getAllWords().get(result);
        } catch (Exception e) {
            customAlertBox.OpenAlertBoxExit(FillBlanks.this, "Hata ile karşılaşıldı :(", true);

            return null;
        }
    }

    public void setNewQuestionToUi() {
        try {
            englishWord = getRandomWord();

            question.setText(englishWord.getWord().toUpperCase(Locale.ROOT));
            answerOfQuestion = String.valueOf(englishWord.getTranslations().get(0))
                    .toUpperCase(Locale.ROOT).replaceAll("\"", "");
            usersAnswer.setText("");
            usersAnswer.requestFocus();
        } catch (Exception e) {
            customAlertBox.OpenAlertBoxExit(FillBlanks.this, "Hata ile karşılaşıldı :(", true);
        }
    }

    public Boolean getResultOfAnswer() {
        // answerOfQuestion.equals(answerOfUser)

        String transition = "";
        List<String> transitionList = englishWord.getTranslations();
        Log.i("GAME-FB", "Transition Length" + transitionList.size());


        for (int i = 0; i < transitionList.size(); i++) {
            transition = transitionList.get(i).toUpperCase(Locale.ROOT).replaceAll("\"", "");

            Log.i("GAME-FB", "Transition " + transition);
            Log.i("GAME-FB", String.valueOf(transition.equals(answerOfUser)));

            if (transition.replaceAll("\"", "").equals(answerOfUser)) {
                Log.i("GAME-FB", "CEVAP DOĞRU2");

                return true;
            }
        }

        return false;
    }

    public void getQuestionHint() {
        usersAnswer.setText("");
        usersAnswer.setHint(answerOfQuestion.substring(0, 1) + Strings.repeat("*", answerOfQuestion.length() - 1));
    }
}