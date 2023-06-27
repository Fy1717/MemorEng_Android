package com.example.memorengandroid.view.Fragments.Areas;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.memorengandroid.R;
import com.example.memorengandroid.controller.WordFilter;
import com.example.memorengandroid.model.EnglishWord;
import com.example.memorengandroid.model.EnglishWords;

import java.util.Locale;


public class FreeWork extends Fragment {
    TextView allwordsText;
    EditText search;
    String allWords;

    EnglishWords englishWordList = EnglishWords.getInstance();
    EnglishWord englishWord;

    public FreeWork() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                System.out.println("NEW TEXT :: " + editable.toString());
                filter(editable.toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_free_work, container, false);
    }

    private void setWordListToScreen() {
        allWords = "";

        for (int i = 0; i < englishWordList.getAllWords().size(); i++) {
            englishWord = englishWordList.getAllWords().get(i);

            allWords += englishWord.getWord() + " = " +
                    String.valueOf(englishWord.getTranslations().get(0))
                            .replaceAll("\"", "") + "\n\n";
        }

        allwordsText.setText(allWords);
    }

    private void filter(String str) {
        if (str.replaceAll(" ", "").equals("")) {
            setWordListToScreen();
        } else {
            WordFilter wordFilter = new WordFilter();
            String resultOfFilter = wordFilter.filterString(str);

            allwordsText.setText(resultOfFilter);
        }
    }
}