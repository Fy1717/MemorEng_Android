package com.example.memorengandroid.view.Fragments.Areas;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.memorengandroid.R;
import com.example.memorengandroid.controller.Word.Filter;
import com.example.memorengandroid.model.EnglishWord;
import com.example.memorengandroid.model.EnglishWords;


public class FreeWork extends Fragment {
    TextView allwordsText;
    EditText search;
    String allWords;
    EnglishWords englishWordList = EnglishWords.getInstance();
    EnglishWord englishWord;

    private static final int REFRESH_DELAY_MS = 3000;
    private Handler handler = new Handler();

    private Runnable refreshRunnable;

    public FreeWork() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpRefreshHandler();

        allwordsText = (TextView) view.findViewById(R.id.allwordstext);
        search = (EditText) view.findViewById(R.id.search);

        setWordListToScreen();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_free_work, container, false);
    }

    private void setWordListToScreen() {
        allWords = "";

        if (englishWordList.getAllWords() != null) {
            for (int i = 0; i < englishWordList.getAllWords().size(); i++) {
                englishWord = englishWordList.getAllWords().get(i);

                allWords += englishWord.getWord() + " = " +
                        String.valueOf(englishWord.getTranslations().get(0))
                                .replaceAll("\"", "") + "\n\n";
            }

            allwordsText.setText(allWords);

            try {
                if (handler != null && refreshRunnable != null) {
                    handler.removeCallbacks(refreshRunnable);
                    handler = null;
                    refreshRunnable = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void filter(String str) {
        if (str.replaceAll(" ", "").equals("")) {
            setWordListToScreen();
        } else {
            Filter wordFilter = new Filter();
            String resultOfFilter = wordFilter.filterString(str);

            allwordsText.setText(resultOfFilter);
        }
    }

    public void setUpRefreshHandler() {
        try {
            refreshRunnable = new Runnable() {
                @Override
                public void run() {
                    setWordListToScreen();

                    if (handler != null && refreshRunnable != null) {
                        handler.postDelayed(this, REFRESH_DELAY_MS);
                    }
                }
            };

            if (handler != null && refreshRunnable != null) {
                handler.post(refreshRunnable);
            }
        } catch (Exception e) {
            System.out.println("YYYYYYYYYYYYYYYYYYYY");

            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        try {
            handler.removeCallbacks(refreshRunnable);
            handler = null;
            refreshRunnable = null;
        } catch (Exception e) {
            System.out.println("ZZZZZZZZZZZZZZZZ");

            e.printStackTrace();
        }
    }
}