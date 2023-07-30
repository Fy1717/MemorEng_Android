package com.example.memorengandroid.view.Pages.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.memorengandroid.R;
import com.example.memorengandroid.adapter.CustomAlertBox;
import com.example.memorengandroid.model.EnglishWords;

import java.util.Locale;
import java.util.Random;

public class MultipleChoice extends AppCompatActivity {
    View viewOfQuestion;
    ImageView closeicon;
    Button option1, option2, option3, option4;
    TextView countOfTrueFalse, count_of_question;
    EnglishWords englishWordList = EnglishWords.getInstance();

    int trueAnswers, falseAnswers, totalAnswered;

    public static TextView question;

    public static int indexOfQuestion;
    public static String answerOfQuestion = "";
    public static String answerOfUser = "";
    public static Button answerOfTrueButton;

    CustomAlertBox customAlertBox = new CustomAlertBox(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        trueAnswers = 0;
        falseAnswers = 0;
        totalAnswered = 1;
        indexOfQuestion = getRandomNumber(0, englishWordList.getAllWords().size());

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        viewOfQuestion = findViewById(R.id.viewOfQuestion);

        countOfTrueFalse = findViewById(R.id.truefalsecounts);
        count_of_question = findViewById(R.id.count_of_question);
        question = findViewById(R.id.question);

        answerListeners();

        if (englishWordList.getAllWords() != null) {
            question.setText(englishWordList.getAllWords().get(indexOfQuestion).getWord().toUpperCase(Locale.ROOT));
            answerOfQuestion = String.valueOf(englishWordList.getAllWords().get(indexOfQuestion).getTranslations().get(0))
                    .toUpperCase(Locale.ROOT).replaceAll("\"", "");

            setAnswersRandom();

            System.out.println("TOPLAM SORU SAYISI : " + englishWordList.getAllWords().size());
        } else {
            finish();
        }

        closeicon = (ImageView) findViewById(R.id.closeicon);

        closeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAlertBox.OpenAlertBoxExit(MultipleChoice.this, "Anasayfaya dönmek istediğinize emin misiniz? ", true);
            }
        });
    }

    public void answerListeners() {
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnswerTheQuestion(option1.getText().toString().toUpperCase(Locale.ROOT).trim(), option1);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnswerTheQuestion(option2.getText().toString().toUpperCase(Locale.ROOT).trim(), option2);
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnswerTheQuestion(option3.getText().toString().toUpperCase(Locale.ROOT).trim(), option3);
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnswerTheQuestion(option4.getText().toString().toUpperCase(Locale.ROOT).trim(), option4);
            }
        });
    }

    public Boolean AnswerTheQuestion(String userAnswer, Button userAnswerButton) {
        answerOfUser = userAnswer;
        Boolean isTrue;

        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
        option4.setEnabled(false);

        if (answerOfQuestion.equals(answerOfUser)) {
            trueAnswers += 1;
            isTrue = true;
        } else {
            falseAnswers += 1;
            isTrue = false;
        }

        totalAnswered += 1;

        if (totalAnswered == 21) {
            endOfTheGame();
        }

        if (isTrue) {
            userAnswerButton.setBackgroundResource(R.drawable.truebutton);
        } else {
            userAnswerButton.setBackgroundResource(R.drawable.falsebutton);
        }

        answerOfTrueButton.setBackgroundResource(R.drawable.truebutton);

        countOfTrueFalse.setText("DOĞRU: " + trueAnswers + "\n\nYANLIŞ: " + falseAnswers);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                indexOfQuestion = getRandomNumber(0, englishWordList.getAllWords().size());

                if (englishWordList.getAllWords().size() <= indexOfQuestion) {
                    String resultMessage = "DOĞRU SAYISI : " + trueAnswers +
                            "\nYANLIŞ SAYISI : " + falseAnswers +
                            "\n\n\nOyun tamamlandı.. Yeniden denemek ister misin?";
                    customAlertBox.OpenAlertBoxExit(MultipleChoice.this, resultMessage, false);
                } else {
                    setAnswersRandom();
                }

                System.out.println("TRUE : " + trueAnswers);
                System.out.println("FALSE : " + falseAnswers);

                option1.setEnabled(true);
                option2.setEnabled(true);
                option3.setEnabled(true);
                option4.setEnabled(true);

                count_of_question.setText(String.valueOf(totalAnswered));
            }
        }, 1500);

        return true;
    }

    public void setAnswersRandom() {
        try {
            option1.setBackgroundResource(R.drawable.option);
            option2.setBackgroundResource(R.drawable.option);
            option3.setBackgroundResource(R.drawable.option);
            option4.setBackgroundResource(R.drawable.option);

            question.setText(englishWordList.getAllWords().get(indexOfQuestion).getWord().toUpperCase(Locale.ROOT));
            answerOfQuestion = String.valueOf(englishWordList.getAllWords().get(indexOfQuestion).getTranslations().get(0))
                    .toUpperCase(Locale.ROOT).replaceAll("\"", "");

            int result = getRandomNumber(0, 4) + 1;

            String randomWrongAnswer1 = getRandomWrongAnswer(indexOfQuestion);
            String randomWrongAnswer2 = getRandomWrongAnswer(indexOfQuestion);
            String randomWrongAnswer3 = getRandomWrongAnswer(indexOfQuestion);

            if (result == 1) {
                option1.setText(answerOfQuestion);
                option2.setText(randomWrongAnswer1);
                option3.setText(randomWrongAnswer2);
                option4.setText(randomWrongAnswer3);

                answerOfTrueButton = option1;
            } else if (result == 2) {
                option2.setText(answerOfQuestion);
                option1.setText(randomWrongAnswer1);
                option3.setText(randomWrongAnswer2);
                option4.setText(randomWrongAnswer3);

                answerOfTrueButton = option2;
            } else if (result == 3) {
                option3.setText(answerOfQuestion);
                option1.setText(randomWrongAnswer1);
                option2.setText(randomWrongAnswer2);
                option4.setText(randomWrongAnswer3);

                answerOfTrueButton = option3;
            } else {
                option4.setText(answerOfQuestion);
                option1.setText(randomWrongAnswer1);
                option2.setText(randomWrongAnswer2);
                option3.setText(randomWrongAnswer3);

                answerOfTrueButton = option4;
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getLocalizedMessage());
        }
    }

    private String getRandomWrongAnswer(int trueAnswersIndex) {
        int randomNumber = getRandomNumber(0, englishWordList.getAllWords().size());

        if (randomNumber == trueAnswersIndex) {
            randomNumber += 1;

            if (randomNumber >= englishWordList.getAllWords().size() - 1) {
                randomNumber -= 2;
            }
        }

        return String.valueOf(englishWordList.getAllWords().get(randomNumber).getTranslations().get(0))
                .toUpperCase(Locale.ROOT).replaceAll("\"", "");
    }

    public int getRandomNumber(int minNum, int maxNum) {
        Random random = new Random();
        int result = random.nextInt(maxNum - minNum);

        return result;
    }

    public void endOfTheGame () {
        question.setVisibility(View.GONE);
        option1.setVisibility(View.GONE);
        option2.setVisibility(View.GONE);
        option3.setVisibility(View.GONE);
        option4.setVisibility(View.GONE);
        count_of_question.setVisibility(View.GONE);

        viewOfQuestion.setVisibility(View.GONE);

        customAlertBox.OpenAlertBoxReGame(MultipleChoice.this, "Oyun bitti, tekrar denemek ister misin?");

        System.out.println(".......END OF GAME......");
    }

    @Override
    public void onBackPressed() {
        customAlertBox.OpenAlertBoxExit(MultipleChoice.this, "Çıkış yapmak istediğinize emin misiniz? ", true);
    }
}