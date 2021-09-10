package com.finalexam.cookingapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.finalexam.cookingapp.view.fragment.IngredientsFragment;
import com.finalexam.cookingapp.view.fragment.PreparationsFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new PreparationsFragment();
            default:
                return new IngredientsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 1:
                title = "Preparations";
                break;
            default:
                title = "Ingredients";
                break;
        }
        return title;
    }
}
