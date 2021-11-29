package com.se7en.theapp;

public class SignModel {
    private String email, password;

    public SignModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
