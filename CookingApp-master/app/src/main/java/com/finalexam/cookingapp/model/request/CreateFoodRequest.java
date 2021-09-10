package com.finalexam.cookingapp.model.request;

import com.finalexam.cookingapp.model.global.storage.DetailIngredient;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateFoodRequest {
    @SerializedName("category_id")
    private Integer categoryID;

    @SerializedName("cover_image_id")
    private int coverImageID;

    @SerializedName("detail_ingredients")
    private List<DetailIngredient> detailIngredient;

    @SerializedName("name")
    private String name;

    @SerializedName("clorie")
    private int clorie;

    @SerializedName("potion")
    private int potion;

    @SerializedName("level")
    private int level;

    @SerializedName("prepare")
    private String prepare;

    @SerializedName("youtube_url")
    private String youtubeUrl;

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getCoverImageID() {
        return coverImageID;
    }

    public void setCoverImageID(int coverImageID) {
        this.coverImageID = coverImageID;
    }

    public List<DetailIngredient> getDetailIngredient() {
        return detailIngredient;
    }

    public void setDetailIngredient(List<DetailIngredient> detailIngredient) {
        this.detailIngredient = detailIngredient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClorie() {
        return clorie;
    }

    public void setClorie(int clorie) {
        this.clorie = clorie;
    }

    public int getPotion() {
        return potion;
    }

    public void setPotion(int potion) {
        this.potion = potion;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPrepare() {
        return prepare;
    }

    public void setPrepare(String prepare) {
        this.prepare = prepare;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public CreateFoodRequest(
            int categoryID, int coverImageID,
            List<DetailIngredient> detailIngredient, String name, int clorie,
            int potion, int level, String prepare, String youtubeUrl
    ) {
        this.categoryID = categoryID;
        this.coverImageID = coverImageID;
        this.detailIngredient = detailIngredient;
        this.name = name;
        this.clorie = clorie;
        this.potion = potion;
        this.level = level;
        this.prepare = prepare;
        this.youtubeUrl = youtubeUrl;
    }

    public CreateFoodRequest() {}
}
