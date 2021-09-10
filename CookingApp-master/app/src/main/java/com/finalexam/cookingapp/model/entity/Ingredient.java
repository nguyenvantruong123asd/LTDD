package com.finalexam.cookingapp.model.entity;

import com.google.gson.annotations.SerializedName;

public class Ingredient {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String ingredientName;

    public Ingredient(int id, String ingredientName) {
        this.id = id;
        this.ingredientName = ingredientName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String toString() {
        return ingredientName;
    }
}
