package com.finalexam.cookingapp;

import android.app.Application;

import com.finalexam.cookingapp.model.global.storage.RecipeData;

public class GlobalStorage extends Application {
    private RecipeData recipeData;
    private static volatile GlobalStorage instance;

    public GlobalStorage() {
        super();
        recipeData = new RecipeData();
    }

    public static GlobalStorage self() {
        if (instance == null)
            instance = new GlobalStorage();
        return instance;
    }

    public RecipeData getRecipeData() {
        return recipeData;
    }

    public void setRecipeData(RecipeData recipeData) {
        this.recipeData = recipeData;
    }
}
