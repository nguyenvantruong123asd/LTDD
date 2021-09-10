package com.finalexam.cookingapp.model.response;

import com.google.gson.annotations.SerializedName;

public class UploadImageResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("image")
    private String imageName;


    public UploadImageResponse(int id, String imageName) {
        this.id = id;
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
