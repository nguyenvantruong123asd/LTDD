package com.finalexam.cookingapp.model.entity;

import com.finalexam.cookingapp.model.response.LoginResponse;

public class User {
    private int id;
    private String fullName;
    private String email;
    private Integer currentAccount;

    public User(int id, String fullName, String email, Integer currentAccount) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.currentAccount = currentAccount;
    }

    public User(LoginResponse loginResponse) {
        this.id = loginResponse.getId();
        this.fullName = loginResponse.getFullName();
        this.email = loginResponse.getEmail();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(Integer currentAccount) {
        this.currentAccount = currentAccount;
    }
}
