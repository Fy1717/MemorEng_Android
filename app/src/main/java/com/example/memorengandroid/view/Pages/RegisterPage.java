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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.memorengandroid.R;
import com.example.memorengandroid.service.Request.Register;

public class RegisterPage extends AppCompatActivity {

    Button registerButton;
    View loadingLayout;
    TextView loginText;
    EditText nameEditText, surnameEditText, usernameEditText, emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#f0f1f2"));

        nameEditText = findViewById(R.id.name);
        surnameEditText = findViewById(R.id.surname);
        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);

        loginText = findViewById(R.id.loginText);

        loadingLayout = findViewById(R.id.loading_layout);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterPage.this.runOnUiThread(new Runnable() {
                    public void run() {
                        loadingLayout.setVisibility(View.VISIBLE);
                    }
                });

                controlRegister(
                        nameEditText.getText().toString().trim(),
                        surnameEditText.getText().toString().trim(),
                        usernameEditText.getText().toString().trim(),
                        emailEditText.getText().toString().trim(),
                        passwordEditText.getText().toString().trim());
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(intent);
            }
        });
    }

    public void controlRegister(String name, String surname, String username, String email, String password) {
        Register model = new ViewModelProvider(this).get(Register.class);

        model.getRegisterStatus(name, surname, username, email, password)
                .observe(this, state -> {
                    Log.i("REGISTER", "STATE : " + state);

                    if (state) {
                        Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                        startActivity(intent);
                    } else {
                        if (errorHandlerModel.getRegisterErrorMessage() != null && !errorHandlerModel.getRegisterErrorMessage().equals("")) {
                            Toast.makeText(RegisterPage.this, errorHandlerModel.getRegisterErrorMessage(), Toast.LENGTH_LONG).show();
                        }

                        RegisterPage.this.runOnUiThread(new Runnable() {
                            public void run() {
                                loadingLayout.setVisibility(View.GONE);
                            }
                        });
                    }
                });
    }
}
