package com.example.memorengandroid.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.memorengandroid.view.Fragments.Areas.Category;
import com.example.memorengandroid.view.Fragments.Areas.FreeWork;
import com.example.memorengandroid.view.Fragments.Areas.Game;
import com.example.memorengandroid.view.Fragments.Areas.Level;
import com.example.memorengandroid.view.Fragments.Areas.UserStatus;

public class AreaAdapter extends FragmentStateAdapter {

    public AreaAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new UserStatus();
        } else if ( position == 1) {
            return new Game();
        } else if ( position == 2) {
            return new Level();
        } else if ( position == 3) {
            return new FreeWork();
        }

        return new Category();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
