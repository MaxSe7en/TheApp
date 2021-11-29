package com.se7en.theapp;

public class RegisterModel {
    private String username;
    private String email;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public RegisterModel(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
