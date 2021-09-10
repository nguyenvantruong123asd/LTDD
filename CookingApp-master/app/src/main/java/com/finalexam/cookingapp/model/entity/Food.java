package com.finalexam.cookingapp.model.entity;

import com.google.gson.annotations.SerializedName;

public class Food {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("prepare")
    private String prepare;

    @SerializedName("cover_image_id")
    private int imageID;
}
