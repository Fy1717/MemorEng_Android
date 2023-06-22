package com.example.memorengandroid.view.Pages;

import static com.example.memorengandroid.service.ApiModel.ErrorHandlerModel.errorHandlerModel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.example.memorengandroid.R;
import com.example.memorengandroid.adapter.AreaAdapter;
import com.example.memorengandroid.model.User;
import com.example.memorengandroid.service.Request.GetEnglish;
import com.example.memorengandroid.service.Request.Login;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class MainAreaPage extends AppCompatActivity {
    Button searchWordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_area_page);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#f0f1f2"));

        searchWordButton = findViewById(R.id.search_word_button);
        searchWordButton.setOnClickListener(v -> showDialog());

        setViewPager();

        controlEnglishWords();
    }

    private void setViewPager() {
        ViewPager2 viewPager = findViewById(R.id.area_pagination);
        AreaAdapter areaAdapter;
        FragmentManager fragmentManager = getSupportFragmentManager();

        areaAdapter = new AreaAdapter(fragmentManager, getLifecycle());
        viewPager.setAdapter(areaAdapter);

        WormDotsIndicator dotsIndicator = findViewById(R.id.worm_dots_indicator);
        dotsIndicator.attachTo(viewPager);
    }

    private void showDialog() {
        MainAreaPage.this.runOnUiThread(() -> {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottom_sheet_word_search);

            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        });
    }

    public void controlEnglishWords() {
        GetEnglish model = new ViewModelProvider(this).get(GetEnglish.class);

        User user = User.getInstance();

        Log.i("USER-ENGLISH", user.getAccessToken());

        model.getEnglishStatus()
                .observe(this, state -> {
                    Log.i("ENGLISH", "STATE : " + state);

                    if (state) {
                        Log.i("ENGLISH", "STATE : " + state);
                    } else {
                        if (errorHandlerModel.getGetEnglishWordsErrorMessage() != null &&
                                !errorHandlerModel.getGetEnglishWordsErrorMessage().equals("")) {
                            Toast.makeText(MainAreaPage.this,
                                    errorHandlerModel.getGetEnglishWordsErrorMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {}
}