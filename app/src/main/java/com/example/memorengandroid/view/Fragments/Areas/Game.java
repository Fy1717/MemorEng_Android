package com.example.memorengandroid.view.Fragments.Areas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.memorengandroid.R;
import com.example.memorengandroid.view.Pages.Games.FillBlanks;
import com.example.memorengandroid.view.Pages.Games.MatchWords;
import com.example.memorengandroid.view.Pages.Games.MultipleChoice;
import com.example.memorengandroid.view.Pages.Games.Wordle;


public class Game extends Fragment {
    View fillBlanks, wordle, multipleChoice, matchWords;

    public Game() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fillBlanks = view.findViewById(R.id.game_fill_blanks);
        wordle = view.findViewById(R.id.game_wordle);
        multipleChoice = view.findViewById(R.id.game_multiple_choice);
        matchWords = view.findViewById(R.id.game_match_words);

        fillBlanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FillBlanks.class);
                startActivity(intent);
            }
        });

        wordle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Wordle.class);
                startActivity(intent);
            }
        });

        multipleChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MultipleChoice.class);
                startActivity(intent);
            }
        });

        matchWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MatchWords.class);
                startActivity(intent);
            }
        });
    }
}