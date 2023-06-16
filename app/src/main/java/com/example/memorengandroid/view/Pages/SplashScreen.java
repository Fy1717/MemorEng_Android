package com.example.memorengandroid.view.Pages;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memorengandroid.R;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#f0f1f2"));

        Intent intent = new Intent(getApplicationContext(), RegisterPage.class);
        startActivity(intent);
    }
}