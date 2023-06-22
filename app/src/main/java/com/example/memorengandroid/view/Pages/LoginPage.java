package com.example.memorengandroid.view.Pages;

import static com.example.memorengandroid.service.ApiModel.ErrorHandlerModel.errorHandlerModel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.memorengandroid.R;
import com.example.memorengandroid.service.Request.Login;
import com.example.memorengandroid.service.Request.Register;

public class LoginPage extends AppCompatActivity {
    Button loginButton;

    View loadingLayout;
    EditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#f0f1f2"));

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);

        loadingLayout = findViewById(R.id.loading_layout);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginPage.this.runOnUiThread(new Runnable() {
                    public void run() {
                        loadingLayout.setVisibility(View.VISIBLE);
                    }
                });

                controlLogin(
                        emailEditText.getText().toString().trim(),
                        passwordEditText.getText().toString().trim());
            }
        });
    }

    public void controlLogin(String email, String password) {
        Login model = new ViewModelProvider(this).get(Login.class);

        model.getLoginStatus(email, password)
                .observe(this, state -> {
                    Log.i("LOGIN", "STATE : " + state);

                    if (state) {
                        Intent intent = new Intent(LoginPage.this, MainAreaPage.class);
                        startActivity(intent);
                    } else {
                        if (errorHandlerModel.getLoginErrorMessage() != null && !errorHandlerModel.getLoginErrorMessage().equals("")) {
                            Toast.makeText(LoginPage.this, errorHandlerModel.getLoginErrorMessage(), Toast.LENGTH_LONG).show();
                        }

                        LoginPage.this.runOnUiThread(new Runnable() {
                            public void run() {
                                loadingLayout.setVisibility(View.GONE);
                            }
                        });
                    }
                });
    }
}