package com.example.memorengandroid.view.Pages;

import static com.example.memorengandroid.service.ApiModel.ErrorHandlerModel.errorHandlerModel;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;
import com.example.memorengandroid.R;
import com.example.memorengandroid.adapter.AreaAdapter;
import com.example.memorengandroid.controller.NotificationController.SetupNotification;
import com.example.memorengandroid.controller.Word.Filter;
import com.example.memorengandroid.model.EnglishWords;
import com.example.memorengandroid.model.Repository.RoomDB.UserRoomController;
import com.example.memorengandroid.model.User;
import com.example.memorengandroid.service.Request.GetEnglish;
import com.example.memorengandroid.service.Request.Login;
import com.example.memorengandroid.service.Request.Logout;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class MainAreaPage extends AppCompatActivity {
    Button searchWordButton, rightMenuButton;
    User user = User.getInstance();
    EnglishWords englishWords = EnglishWords.getInstance();
    SwipeRefreshLayout pullToRefresh;
    UserRoomController userRoomController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_area_page);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#f0f1f2"));

        pullToRefresh = findViewById(R.id.swipeRefreshLayout);

        searchWordButton = findViewById(R.id.search_word_button);
        searchWordButton.setOnClickListener(v -> showDialog());

        rightMenuButton = findViewById(R.id.profile_button);
        rightMenuButton.setOnClickListener(v -> showProfileDialog());

        setViewPager();
        controlEnglishWords();

        // TODO : add
        //notificationChannel();
        setRefreshing();
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
        try {
            MainAreaPage.this.runOnUiThread(() -> {
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.bottom_sheet_word_search);

                TextView result_of_search = dialog.findViewById(R.id.result_of_search);
                EditText search = dialog.findViewById(R.id.search_edit_text);

                search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String str = editable.toString().replaceAll(" ", "");

                        if (!str.equals("")) {
                            Filter wordFilter = new Filter();
                            String resultOfFilter = wordFilter.filterString(str);

                            result_of_search.setText(resultOfFilter);
                        } else {
                            result_of_search.setText("");
                        }
                    }
                });

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showProfileDialog() {
        try {
            MainAreaPage.this.runOnUiThread(() -> {
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.right_side_bar);

                ImageView closeButton = dialog.findViewById(R.id.close_button);
                closeButton.setOnClickListener(v -> dialog.dismiss());

                Button exitButton = dialog.findViewById(R.id.exit_button);
                exitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logout();

                        Intent intent = new Intent(MainAreaPage.this, LoginPage.class);
                        startActivity(intent);

                        finish();

                        dialog.dismiss();
                    }
                });

                TextView username = dialog.findViewById(R.id.username);

                if (user.getUsername() != null) {
                    username.setText(user.getUsername().replaceAll("\"", ""));
                } else {
                    username.setText("UNKNOWN");
                }

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.ProfileAnimation;
                dialog.getWindow().getExitTransition().setDuration(400);
                //dialog.getWindow().setGravity(Gravity.RIGHT);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void controlEnglishWords() {
        Log.i("USER-ENGLISH", user.getAccessToken());

        GetEnglish model = new ViewModelProvider(this).get(GetEnglish.class);

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

        pullToRefresh.setRefreshing(false);
    }

    // TODO : add
    public void notificationChannel() {
        if (englishWords.getAllWords() == null) {
            SetupNotification channel = new SetupNotification(this);

            channel.startNotification(15, 00, "BOARD : TAHTA");
        }
    }

    public void setRefreshing() {
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (englishWords.getAllWords() == null) {
                    controlEnglishWords();
                } else {
                    pullToRefresh.setRefreshing(false);
                }
            }
        });
    }

    public void logout() {
        Logout logoutModel = new ViewModelProvider(this).get(Logout.class);

        user.setAccessToken(null);
        //user.setRefreshToken(null);
        user.setEmail(null);
        user.setUsername(null);
        user.setName(null);
        user.setSurname(null);
        user.setId(null);
        user.setIsAnonymous(false);
        user.setAccessTokenExpiration(null);
        user.setRefreshTokenExpiration(null);

        userRoomController = new UserRoomController(this);
        userRoomController.deleteUserInRoom();

        logoutModel.getLogoutStatus()
                .observe(this, state -> {
                    if (state) {
                        user.setRefreshToken(null);
                    }});
    }

    @Override
    public void onBackPressed() {}
}