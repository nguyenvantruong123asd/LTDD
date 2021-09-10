package com.finalexam.cookingapp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("full_name")
    @Expose
    private String fullName;

    public LoginResponse(Integer id, String email, String fullName, String password) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }
}
