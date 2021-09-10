package com.finalexam.cookingapp.model.global.storage;

import com.finalexam.cookingapp.model.entity.Ingredient;
import com.google.gson.annotations.SerializedName;

public class DetailIngredient {
    @SerializedName("ingredient_id")
    private int ingredient_id;

    private Ingredient ingredient;

    @SerializedName("quantity")
    private String quantity;

    public DetailIngredient(Ingredient ingredient, String quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(int ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    @Override
    public String toString() {
        return quantity + " " + ingredient.getIngredientName().toLowerCase();
    }
}
