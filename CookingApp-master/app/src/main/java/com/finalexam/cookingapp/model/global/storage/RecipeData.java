package com.finalexam.cookingapp.model.global.storage;

import java.util.ArrayList;
import java.util.List;

public class RecipeData {
    private List<DetailIngredient> detailIngredients;
    private List<String> detailIngredientsContent;
    private String preparation;
    private String coverImageUrl;

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public RecipeData(List<DetailIngredient> detailIngredients, String preparation) {
        this.detailIngredients = detailIngredients;
        this.preparation = preparation;
    }

    public RecipeData() {
        detailIngredients = new ArrayList<>();
        detailIngredientsContent = new ArrayList<>();
        preparation = "";
    }

    public List<DetailIngredient> getDetailIngredients() {
        return detailIngredients;
    }

    public void setDetailIngredients(List<DetailIngredient> detailIngredients) {
        this.detailIngredients = detailIngredients;
    }

    public void addDetailIngredient(DetailIngredient detailIngredient) {
        this.detailIngredients.add(detailIngredient);
        this.detailIngredientsContent.add(detailIngredient.toString());
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public List<String> getDetailIngredientsContent() {
        return detailIngredientsContent;
    }

    public void setDetailIngredientsContent(List<String> detailIngredientsContent) {
        this.detailIngredientsContent = detailIngredientsContent;
    }
}
