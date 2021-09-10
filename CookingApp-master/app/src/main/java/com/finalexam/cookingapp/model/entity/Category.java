package com.finalexam.cookingapp.model.entity;

import android.app.Activity;

import com.finalexam.cookingapp.R;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    private int id;

    private int image;

    @SerializedName("image_id")
    private String imageID;

    @SerializedName("name")
    private String categoryName;

    public Category(int id, int image, String imageID, String categoryName) {
        this.id = id;
        this.image = image;
        this.imageID = imageID;
        this.categoryName = categoryName;
    }

    public Category(int id, String imageID, String categoryName) {
        this.id = id;
        this.imageID = imageID;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
