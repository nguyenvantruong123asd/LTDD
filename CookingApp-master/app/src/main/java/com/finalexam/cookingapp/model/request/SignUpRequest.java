package com.finalexam.cookingapp.model.request;

import com.google.gson.annotations.SerializedName;

public class SignUpRequest {
    public SignUpRequest(String email, String full_name, String password) {
        this.email = email;
        this.full_name = full_name;
        this.password = password;
    }

    @SerializedName("email")
    private String email;

    @SerializedName("full_name")
    private String full_name;

    @SerializedName("password")
    private String password;
}
