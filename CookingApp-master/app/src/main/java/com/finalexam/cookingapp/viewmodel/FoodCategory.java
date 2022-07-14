package com.finalexam.cookingapp.viewmodel;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalexam.cookingapp.model.entity.Food;

import java.util.List;

public class FoodCategory extends RecyclerView.Adapter<FoodCategory.FoodViewHolder> {
    private List<Food> foods;

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        return null;
    }

    @Override
    public void onBindViewHolder(
            @NonNull FoodCategory.FoodViewHolder holder,
            int position
    ) {

    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        public FoodViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);
        }
    }
}
